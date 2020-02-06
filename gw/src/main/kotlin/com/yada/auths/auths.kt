package com.yada.auths

import com.yada.model.App
import com.yada.model.Operator
import com.yada.model.Res
import com.yada.model.User
import com.yada.services.AppService
import com.yada.services.IUserService
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private fun getPwdDigestString(un: String, pwd: String) = Base64.encodeBase64String(DigestUtils.sha1(un + pwd))
/**
 * 认帐服务接口
 */
interface IAuthenticationService {
    fun login(username: String, password: String): Mono<User>
    fun logout(username: String): Mono<Void>
    fun changePassword(username: String, oldPassword: String, newPassword: String): Mono<Boolean>
}

/***
 * 授权服务接口
 */
interface IAuthorizationService {
    fun authorize(user: User, uri: String, opt: Operator): Mono<Boolean>
    fun getUserResList(appId: String, user: User): Flux<Res>
}

@Service
class AuthenticationService @Autowired constructor(private val userService: IUserService) : IAuthenticationService {
    override fun login(username: String, password: String): Mono<User> =
            userService.getPwd(username).map { it == getPwdDigestString(username, password) }.flatMap {
                if (it) {
                    userService.get(username)
                } else {
                    null
                }
            }

    override fun logout(username: String): Mono<Void> = Mono.create<Void> { it.success() }

    override fun changePassword(username: String, oldPassword: String, newPassword: String): Mono<Boolean> =
            userService.getPwd(username).map { it == getPwdDigestString(username, oldPassword) }.flatMap {
                if (it) {
                    userService.changePwd(username, getPwdDigestString(username, newPassword)).then(Mono.just(true))
                } else {
                    Mono.just(false)
                }
            }
}

private fun svcUri(svcId: String, uri: String) = "/${svcId}${uri}"

@Service
class AuthorizationService @Autowired constructor(private val appService: AppService) : IAuthorizationService {
    override fun authorize(user: User, uri: String, opt: Operator): Mono<Boolean> =
            Flux.fromIterable(user.roles).flatMap { roleId ->
                appService.get(roleId.appId).map { permit(roleId.roleName, uri, opt, it) }
            }.any { it }.defaultIfEmpty(false)

    override fun getUserResList(appId: String, user: User): Flux<Res> {
        val userRoles = user.roles.filter { it.appId == appId }.map { it.roleName }
        return appService.get(appId).map { app ->
            val roles = app.roles.filter { it.name in userRoles }
            val reses = roles.flatMap { it.resources.flatMap { svc -> svc.resources.map { res -> Res(svcUri(svc.id, res.uri), res.ops) } } }
            reses.groupBy { it.uri }.map { entry ->

                Res(entry.key, entry.value.flatMap { it.ops }.toSet())
            }
        }.flatMapMany { Flux.fromIterable(it) }
    }

    /***
     * 判断roleName, uri, opt在app是否有权限
     */
    private fun permit(roleName: String, uri: String, opt: Operator, app: App) = app.roles.firstOrNull { r -> r.name == roleName }?.run {
        resources.map { svcRes -> svcRes.resources.any { res -> svcUri(svcRes.id, res.uri) == uri && opt in res.ops } }
    }?.any { it } ?: false
}