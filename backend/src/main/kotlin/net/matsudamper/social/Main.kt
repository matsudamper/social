package net.matsudamper.social

import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.file
import io.ktor.server.http.content.files
import io.ktor.server.http.content.resource
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.matsudamper.social.activitystreams.PersonResponse
import net.matsudamper.social.base.ObjectMapper
import net.matsudamper.social.base.ServerEnv

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
    install(ContentNegotiation) {
        json(
            json = ObjectMapper.json,
            contentType = ContentType.Application.ActivityJson,
        )
    }

    routing {
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