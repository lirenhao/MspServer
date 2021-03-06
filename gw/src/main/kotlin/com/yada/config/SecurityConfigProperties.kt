package com.yada.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "yada.security")
class SecurityConfigProperties(
        var defaultPwd: String = "changepwd",
        var recaptcha: RecaptchaProperties = RecaptchaProperties(),
        var pwdStrength: Int = 1,
        var token: TokenProperties = TokenProperties()
) {
    enum class RecaptchaType { None, Google, GoogleCN }

    data class RecaptchaProperties(
            var type: RecaptchaType = RecaptchaType.None,
            var secret: String = "",
            var sitekey: String = "",
            var proxyHost: String = "",
            var proxyPort: Int = 80
    )

    data class TokenProperties(var secret: String = "yadajwt", var expire: Long = 3600)
}