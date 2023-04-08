package net.matsudamper.social.backend.graphql

import kotlin.time.Duration.Companion.minutes
import io.ktor.server.application.ApplicationCall
import net.matsudamper.social.backend.base.ServerEnv

class SocialGraphQlContext(
    val call: ApplicationCall,
) {
    fun setCookie(key: String, value: String) {
        println("setCookie")
        call.response.cookies.append(
            name = key,
            value = value,
            maxAge = 10.minutes.inWholeSeconds,
            domain = ServerEnv.domain,
            path = ".",
            secure = true,
        )
    }
}