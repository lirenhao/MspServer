package com.yada.ssp.user.security

import com.fasterxml.jackson.annotation.JsonProperty
import com.yada.ssp.user.config.EmailCodeProperties
import com.yada.ssp.user.model.EmailCode
import com.yada.ssp.user.repository.EmailCodeRepository
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.context.Context
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

interface IEmailCodeService {
    fun send(key: String, email: String): Mono<Boolean>
    fun check(key: String, code: String): Mono<Boolean>
}

private val sdf = SimpleDateFormat("yyyyMMddHHmmss")

private fun genCode(len: Int = 6): String = (0 until len)
        .map {
            (0 until 10).random()
        }.joinToString("")

private fun isAble(dataTime: String?, expire: Int): Boolean =
        try {
            val curTime = System.currentTimeMillis()
            val hisTime: Long = sdf.parse(dataTime).time + (expire * 1000)
            curTime < hisTime
        } catch (e: ParseException) {
            false
        }

open class HttpEmailCodeService constructor(
        private val config: EmailCodeProperties,
        private val emailCodeRepo: EmailCodeRepository,
        private val webClient: WebClient,
        private val templateEngine: ITemplateEngine
) : IEmailCodeService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun send(key: String, email: String): Mono<Boolean> = emailCodeRepo
            .save(EmailCode(key, email, genCode(), sdf.format(Date())))
            .flatMap {
                val ctx = Context(Locale.ENGLISH)
                ctx.setVariable("code", it.code)
                logger.info("E-mail to [{}] send start! [{}]", email, it.code)
                webClient.post()
                        .uri(config.http.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(object{
                            @JsonProperty("CODE") val code = "0911".padEnd(4)
                            @JsonProperty("RECEIVER") val email = it.email.padEnd(100)
                            @JsonProperty("SENDERMAIL") val form = config.form.padEnd(50)
                            @JsonProperty("SENDERNAME") val name = config.name.padEnd(50)
                            @JsonProperty("SUBJECT") val subject = config.subject.padEnd(100)
                            @JsonProperty("CONTENT") val content = templateEngine.process("email", ctx).padEnd(2048)
                            @JsonProperty("SGTRACENO") val sgTraceNo = it.code.padEnd(6)
                            @JsonProperty("CHECKSUM") val checkSum = DigestUtils.sha256Hex(it.code + config.http.key)
                        })
                        .retrieve()
                        .bodyToMono(object : ParameterizedTypeReference<Map<String, String>>() {})
            }.map {
                logger.info("E-mail to send response! [{}]", it)
                it["message code"] == "0000000"
            }.switchIfEmpty {
                logger.warn("E-mail to [{}] send failed! [{}]", email, "save code error")
                Mono.just(false)
            }.doOnError {
                logger.warn("E-mail to [{}] send failed! [{}]", email, it.message)
                throw it
            }

    override fun check(key: String, code: String): Mono<Boolean> = emailCodeRepo.findById(key)
            .flatMap {
                if (isAble(it.dataTime, config.expire)) {
                    Mono.just(it.code == code)
                } else {
                    Mono.just(false)
                }
            }
            .switchIfEmpty(Mono.just(false))

}

open class SmtpEmailCodeService constructor(
        private val config: EmailCodeProperties,
        private val emailCodeRepo: EmailCodeRepository,
        private val emailSender: JavaMailSender,
        private val templateEngine: ITemplateEngine
) : IEmailCodeService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun send(key: String, email: String): Mono<Boolean> = emailCodeRepo
            .save(EmailCode(key, email, genCode(), sdf.format(Date())))
            .flatMap {
                val mailMessage = emailSender.createMimeMessage()
                val messageHelper = MimeMessageHelper(mailMessage)
                messageHelper.setTo(it.email)
                messageHelper.setFrom(config.form)
                messageHelper.setSubject(config.subject)
                val ctx = Context(Locale.ENGLISH)
                ctx.setVariable("code", it.code)
                messageHelper.setText(templateEngine.process("email", ctx), true)

                emailSender.send(mailMessage)
                logger.info("E-mail to [{}] send success!", email)
                Mono.just(true)
            }.switchIfEmpty {
                logger.warn("E-mail to [{}] send failed! [{}]", email, "save code error")
                Mono.just(false)
            }.doOnError {
                logger.warn("E-mail to [{}] send failed! [{}]", email, it.message)
                throw it
            }

    override fun check(key: String, code: String): Mono<Boolean> = emailCodeRepo.findById(key)
            .flatMap {
                if (isAble(it.dataTime, config.expire)) {
                    Mono.just(it.code == code)
                } else {
                    Mono.just(false)
                }
            }
            .switchIfEmpty(Mono.just(false))

}
