package net.matsudamper.social.base

import kotlinx.serialization.json.Json

object ObjectMapper {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
}
