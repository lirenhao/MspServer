package com.yada.ssp.user.config

import com.yada.ssp.user.repository.EmailCodeRepository
import com.yada.ssp.user.security.HttpEmailCodeService
import com.yada.ssp.user.security.IEmailCodeService
import com.yada.ssp.user.security.SmtpEmailCodeService
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.reactive.function.client.WebClient
import org.thymeleaf.ITemplateEngine
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient

@Configuration
@EnableConfigurationProperties(SecurityConfigProperties::class)
class EmailCodeConfig(
        private val config: EmailCodeProperties,
        private val emailCodeRepo: EmailCodeRepository,
        private val emailSender: JavaMailSender,
        @Qualifier("templateEngine") private val templateEngine: ITemplateEngine
) {

    @Bean
    fun emailCodeService(): IEmailCodeService = when (config.type) {
        EmailCodeProperties.EmailCodeType.Smtp ->
            SmtpEmailCodeService(config, emailCodeRepo, emailSender, templateEngine)
        EmailCodeProperties.EmailCodeType.Http -> {
            val webClient = when (config.http.ssl) {
                false -> {
                    val sslContext = SslContextBuilder
                            .forClient()
                            .trustManager(InsecureTrustManagerFactory.INSTANCE)
                            .build()
                    val httpClient: HttpClient = HttpClient.create()
                            .wiretap(true)
                            .secure { t -> t.sslContext(sslContext) }
                    WebClient.builder().clientConnector(ReactorClientHttpConnector(httpClient)).build()
                }
                else -> WebClient.builder().build()
            }
            HttpEmailCodeService(config, emailCodeRepo, webClient, templateEngine)
        }
        EmailCodeProperties.EmailCodeType.Mock ->
            object : IEmailCodeService {
                override fun send(key: String, email: String): Mono<Boolean> = Mono.just(true)
                override fun check(key: String, code: String): Mono<Boolean> = Mono.just(true)
            }
    }
}