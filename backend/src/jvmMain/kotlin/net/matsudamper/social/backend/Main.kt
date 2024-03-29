package net.matsudamper.social.backend

import java.io.File
import graphql.ExecutionInput
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.file
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.forwardedheaders.ForwardedHeaders
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.server.request.header
import io.ktor.server.request.path
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondText
import io.ktor.server.routing.accept
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import net.matsudamper.social.backend.activitystreams.PersonResponse
import net.matsudamper.social.backend.base.CustomLogger
import net.matsudamper.social.backend.base.ObjectMapper
import net.matsudamper.social.backend.base.ServerEnv
import net.matsudamper.social.backend.graphql.SocialGraphQlContext
import net.matsudamper.social.backend.graphql.SocialGraphQlSchema
import org.slf4j.event.Level

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("logback.configurationFile", "logback.xml")
            embeddedServer(
                CIO,
                port = ServerEnv.port,
                module = Application::myApplicationModule,
                configure = {

                },
            ).start(wait = true)
        }
    }
}

private val apiClient = ApiClient()

fun Application.myApplicationModule() {
    install(ForwardedHeaders)
    install(XForwardedHeaders)
    install(ContentNegotiation) {
        json(
            json = ObjectMapper.json,
            contentType = ContentType.Application.ActivityJson,
        )
        json(
            json = ObjectMapper.json,
            contentType = ContentType.Application.Json,
        )
    }
    install(CallLogging) {
        level = Level.WARN
        filter { call ->
            CustomLogger.General.debug(
                buildString {
                    appendLine("==========${call.request.path()}==========")
                    appendLine(
                        call.request.headers.entries()
                            .map { (key, value) ->
                                "$key=$value"
                            }
                            .joinToString("\n"),
                    )
                },
            )
            true
        }
    }

    routing {
        accept(ContentType.Application.Json) {
            post("/query") {
                val request = call.receive<GraphQlRequest>()

                val executionInputBuilder = ExecutionInput.newExecutionInput()
                    .localContext(SocialGraphQlContext(call))
                    .query(request.query)
                    .variables(request.variables)

                val result = SocialGraphQlSchema.graphql
                    .execute(executionInputBuilder)
                result.errors.onEach {
                    println("===========error")
                    println("error=${it.message}")
                }
                call.respond(result.toString())
            }
        }

        get(".well-known/webfinger") {
            println("webfing=================================")
            val resource = call.parameters["resource"]!!
            val name = "acct:(.+?)@${ServerEnv.domain}".toRegex()
                .find(resource)
                ?.groupValues
                ?.get(1)
            println("name=$name")
            call.respondText(
                contentType = ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError,
                text = "",
            )
        }
//        get(".well-known/host-meta") {
//            call.respondText(
//                contentType = ContentType.Application.Xml,
//                text = """
//                    <?xml version="1.0"?>
//                    <XRD xmlns="http://docs.oasis-open.org/ns/xri/xrd-1.0">
//                        <Link rel="lrdd" type="application/xrd+xml" template="https://${ServerEnv.domain}/.well-known/webfinger?resource={uri}" />
//                    </XRD>
//                """.trimIndent(),
//            )
//        }
        File(ServerEnv.frontPath).listFiles().orEmpty()
            .filterNot { "index.html" == it.name }
            .forEach {
                file("/${it.name}", it.path)
            }
        accept(ContentType.Text.Html) {
            get("{...}") {
                call.respondFile(
                    file = File(ServerEnv.htmlPath),
                )
            }
        }
        accept(ContentType.parse("application/activity+json")) {
            get("/@{userId}") {
                val userId = call.parameters["userId"]!!

//                val person = apiClient.getPersonJson("https://mstdn.jp/@matsudamper")
                println("json=$json")
                call.respondText(
                    contentType = ContentType.Application.ActivityJson,
                    text = json,
//                        .replace("mstdn.jp", "social.matsudamper.net")
                )
                call.respond(
                    PersonResponse(
                        url = "https://${ServerEnv.domain}/@$userId",
                        name = userId,
                        preferredUsername = userId,
                        summary = "<p>a</p>",
                        inbox = "https://${ServerEnv.domain}/inbox",
                        outbox = "https://${ServerEnv.domain}/outbox",
                        context = listOf(
                            "https://www.w3.org/ns/activitystreams",
                        ),
                    ),
                )
            }

            get("/test") {
                val person = apiClient.getPerson("https://mstdn.jp/@matsudamper")

                call.respond(
                    HttpStatusCode.OK,
                    "result->${person.getOrThrow()}",
                )
            }
        }
    }
}

private val json = """
    {
      "@context": [
        "https://www.w3.org/ns/activitystreams",
        "https://w3id.org/security/v1"
      ],
      "id": "https://${ServerEnv.domain}/@matsudamper",
      "type": "Person",
      "preferredUsername": "matsudamper",
      "name": "マツダンパー",
      "url": "https://${ServerEnv.domain}/@matsudamper"
    }
""".trimIndent()