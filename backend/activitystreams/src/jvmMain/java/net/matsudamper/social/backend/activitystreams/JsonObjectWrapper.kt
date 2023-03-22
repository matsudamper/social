package net.matsudamper.social.backend.activitystreams

import kotlinx.serialization.json.*
import net.matsudamper.social.backend.base.CustomLogger
import net.matsudamper.social.backend.base.ObjectMapper

data class JsonObjectWrapper(
    val jsonObject: JsonObject
) {
    fun string(key: String): String {
        val primitive = jsonObject[key]!!.jsonPrimitive
        return if (primitive.isString) {
            primitive.content
        } else {
            throw ParseFailException(jsonObject, key)
        }
    }

    fun stringOrNull(key: String): String? {
        val jsonPrimitive = jsonObject[key]?.jsonPrimitiveOrNull ?: return null
        return if (jsonPrimitive.isString) {
            jsonPrimitive.content
        } else {
            null
        }
    }

    fun boolOrNull(key: String): Boolean? {
        return jsonObject[key]?.jsonPrimitiveOrNull?.booleanOrNull
    }

    inline fun <reified T> parseOrNull(key: String): T? {
        val element = jsonObject[key] ?: return null

        return runCatching {
            ObjectMapper.json.decodeFromJsonElement<T>(element)
        }.onFailure {
            CustomLogger.ParseFail.log(
                T::class,
                buildString {
                    appendLine("parse failed")
                    appendLine(element.toString())
                }
            )
        }.getOrNull()
    }

    private val JsonElement.jsonPrimitiveOrNull: JsonPrimitive? get() = this as? JsonPrimitive
}