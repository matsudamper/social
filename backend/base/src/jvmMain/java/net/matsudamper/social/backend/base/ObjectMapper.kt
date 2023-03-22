package net.matsudamper.social.backend.base

import kotlinx.serialization.json.Json

object ObjectMapper {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
}
