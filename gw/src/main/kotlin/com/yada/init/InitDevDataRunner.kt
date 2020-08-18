package com.yada.init

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.yada.web.model.Org
import com.yada.web.model.Role
import com.yada.web.model.Svc
import com.yada.web.model.User
import com.yada.web.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private val orgJson = """
[
  {
    "id": "00",
    "name": "总行"
  },
  {
    "id": "001",
    "name": "新加坡分行"
  },
  {
    "id": "001001",
    "name": "新加坡皮草"
  }
]
""".trimIndent()

private val userJson = """
[
  {
    "id": "104767999000004@admin",
    "orgId": "001",
    "roles": [
      "admin"
    ],
    "status": "00",
    "email": "email@user.com"
  }
]
""".trimIndent()

private val adminJson = """
[
  "admin"
]
""".trimIndent()

private val roleJson = """
[
  {
    "id": "admin",
    "svcs": [
        {
            "id": "msp",
            "resources": [
                {
                    "uri": "/policy",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/sales/total",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/mer/termNos",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/sales/months",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/eState",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/mer",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/settle/trans",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/settle",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/mer/subs",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/settle/download",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/sales/tops",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/trans/download",
                    "ops": [
                        "READ"
                    ]
                },
                {
                    "uri": "/trans",
                    "ops": [
                        "READ"
                    ]
                }
            ]
        }
    ]
  }
]
""".trimIndent()

private val svcJson = """
[
  {
    "id": "msp",
    "resources": [
        {
            "uri": "/policy",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/sales/total",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/mer/termNos",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/sales/months",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/eState",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/mer",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/settle/trans",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/settle",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/mer/subs",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/settle/download",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/sales/tops",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/trans/download",
            "ops": [
                "READ"
            ]
        },
        {
            "uri": "/trans",
            "ops": [
                "READ"
            ]
        }
    ]
  }
]
""".trimIndent()

@Order(1)
@Profile("dev")
@Component
open class InitDevDataRunner @Autowired constructor(
        private val orgSvc: IOrgService,
        private val usrSvc: IUserService,
        private val roleSvc: IRoleService,
        private val svcSvc: ISvcService,
        private val adminSvc: IAdminUserService,
        private val reactiveMongoTemplate: ReactiveMongoTemplate
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val orgs = jacksonObjectMapper().readValue<List<Org>>(orgJson)
        val svcs = jacksonObjectMapper().readValue<List<Svc>>(svcJson)
        val apps = jacksonObjectMapper().readValue<List<Role>>(roleJson)
        val usrs = jacksonObjectMapper().readValue<List<User>>(userJson)
        val adms = jacksonObjectMapper().readValue<List<String>>(adminJson)

        reactiveMongoTemplate.mongoDatabase.flatMap { Mono.from(it.drop()) }
                .then(initMongoDbCollection(reactiveMongoTemplate))
                .thenMany(Flux.mergeSequential(orgs.map { orgSvc.createOrUpdate(it) }))
                .thenMany(Flux.mergeSequential(svcs.map { svcSvc.createOrUpdate(it) }))
                .thenMany(Flux.mergeSequential(apps.map { roleSvc.createOrUpdate(it) }))
                .thenMany(Flux.mergeSequential(usrs.map { usrSvc.createOrUpdate(it) }))
                .thenMany(Flux.mergeSequential(adms.map { adminSvc.createUser(it) }))
                .collectList().block()
    }
}