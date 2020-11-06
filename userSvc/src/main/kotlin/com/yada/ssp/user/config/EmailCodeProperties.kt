package com.yada.ssp.user.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "yada.email.code")
class EmailCodeProperties(
        var type: EmailCodeType = EmailCodeType.Mock,
        var expire: Int = 0,
        var form: String = "",
        var name: String = "",
        var subject: String = "",
        var http: HttpProperties = HttpProperties()
) {
    enum class EmailCodeType { Smtp, Http, Mock }

    data class HttpProperties(
            var url: String = "",
            var key: String = "",
            var ssl: Boolean = true
    )
}
