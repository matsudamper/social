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
                port = 8090,
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
        json(json = ObjectMapper.json)
    }

    routing {
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

                call.respond(
                    PersonResponse(
                        url = "https://${ServerEnv.domain}/@$userId",
                        name = userId,
                        preferredUsername = userId,
                        summary = "<p>a</p>",
                        inbox = "",
                        outbox = "",
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
                    person.getOrThrow().toString(),
                )
            }
        }
    }
}
