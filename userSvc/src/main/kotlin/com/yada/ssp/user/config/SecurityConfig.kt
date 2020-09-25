package com.yada.ssp.user.config

import com.yada.ssp.user.security.*
import org.jasypt.encryption.StringEncryptor
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SecurityConfigProperties::class)
open class SecurityConfig(
        private val config: SecurityConfigProperties,
        private val stringEncryptor: StringEncryptor
) {

    @Bean
    open fun pwdDigestService(): IPwdDigestService = PwdDigestService(config.defaultPwd, stringEncryptor)

    @Bean
    open fun pwdStrengthService(): IPwdStrengthService = PwdStrengthService(config.pwdStrength)

    @Bean
    open fun recaptchaService(): IRecaptchaService = when (config.recaptcha.type) {
        SecurityConfigProperties.RecaptchaType.Google -> GoogleRecaptchaService(config.recaptcha)
        SecurityConfigProperties.RecaptchaType.GoogleCN -> GoogleCnRecaptchaService(config.recaptcha)
        else -> NoneRecaptchaService()
    }
}
