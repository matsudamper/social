package net.matsudamper.social.backend.activitystreams

import kotlinx.serialization.json.JsonObject

class ParseFailException(
    jsonObject: JsonObject,
    key: String
) : Exception() {
    override val message: String = buildString {
        appendLine("$key is not found")
        appendLine(jsonObject.toString())
    }
}
