package com.yada.web.services

import com.yada.gateways.SvcRoutePredicateFactory
import com.yada.web.model.Res
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.config.GatewayProperties
import org.springframework.cloud.gateway.support.ConfigurationService
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.util.*

interface IActualSvcResOfServerService {
    fun get(svcId: String): Mono<List<Res>>
}

/**
 *
 * # 真实的service资源
 *
 * ## 关于动态更新路由的一些问题(目前不支持)
 *
 * 目前只能注入[ConfigurationService]来准确的获取svcId，不能从[RouteLocator][org.springframework.cloud.gateway.route.RouteLocator]
 * 读取[SvcRoutePredicateFactory.Config], 因此就算写一个[ApplicationListener][org.springframework.context.ApplicationListener]去监听
 * [RefreshRoutesEvent][org.springframework.cloud.gateway.event.RefreshRoutesEvent], 也不能读取[SvcRoutePredicateFactory.Config]，
 *
 * 参考[GatewayControllerEndpoint.routes][org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint.routes]方法，它现在
 * 也没实现这个问题，但是他有TODO等待完成，因此等待它去完善这个再去实现动态更新。
 *
 */
class ActualSvcResOfServerService @Autowired constructor(
        private val context: ApplicationContext
) : IActualSvcResOfServerService {

    override fun get(svcId: String): Mono<List<Res>> = getSvcServerUrl(svcId).map { url ->
        WebClient.create(url)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono<List<Res>>()
    }.orElse(Mono.empty())

    private fun getSvcServerUrl(svcId: String): Optional<String> {
        val properties = context.getBean(GatewayProperties::class.java)
        val env = context.getBean(Environment::class.java)
        val svcRoutePredicateFactory = context.getBean(SvcRoutePredicateFactory::class.java)
        val configurationService = context.getBean(ConfigurationService::class.java)

        val list = properties.routes.mapNotNull { routeDefinition ->
            val appPredicate = routeDefinition.predicates.firstOrNull { it.name == "Svc" }
            if (appPredicate != null) {
                // org.springframework.boot.web.serve.AbstractConfigurableWebServerFactory默认端口：8080
                val port = env.getProperty("server.port") ?: "8080"
                val schema = if (env.getProperty("server.ssl.key-store") == null) "http" else "https"
                val config = configurationService
                        .with(svcRoutePredicateFactory)
                        .name(appPredicate.name)
                        .properties(appPredicate.args)
                        .bind()
                        as SvcRoutePredicateFactory.Config
                val url = "$schema://localhost:$port" +
                        UriComponentsBuilder
                                .fromPath(config.pathPrefix)
                                .pathSegment(config.svcId)
                                .pathSegment("res_list")
                                .encode()
                                .build()
                                .toUriString()

                Pair(config.svcId, url)
            } else null
        }

        return Optional.ofNullable(list.filter { it.first == svcId }.firstOrNull()?.second)
    }

}