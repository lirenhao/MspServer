package com.yada

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 当[m]没有数据时返回404
 */
fun <T> withNotFound(m: Mono<T>, msg: String? = null): Mono<T> = m.switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, msg)))

/**
 * 网上抄的一个kotlin通用logger实现
 * 用的是第三种方法，属性委托
 * 网址：https://www.reddit.com/r/Kotlin/comments/8gbiul/slf4j_loggers_in_3_ways/
 */
class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {
    /**
     * 匿名的伴生对象
     */
    companion object {
        private fun <T> createLogger(clazz: Class<T>) : Logger {
            return LoggerFactory.getLogger(clazz)
        }
    }

    private var logger: Logger? = null

    /**
     * 重写取值方法。
     * [thisRef]为被委托属性的所在对象引用，[property]为被委托属性的元数据
     */
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger {
        if (logger == null) {
            logger = createLogger(thisRef!!.javaClass)
        }
        return logger!!
    }
}