package net.matsudamper.social.backend.activitystreams

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import net.matsudamper.social.backend.base.CustomLogger


data class ActivityStreamReceivePerson(
    val context: List<ActivityPubContext>,
    val type: String,
    val url: String,
    // 表示名
    val name: String,
    // 重複なしの厳密な名前
    val preferredUsername: String,
    // HTML
    val summary: String?,
    val inbox: String,
    val outbox: String,
    val icon: Image?,
    val image: Image?,
    val id: String,
    val following: String?,
    val followers: String?,
    // ピン留めされたアイテムのエンドポイント
    val featured: String?,
    val featuredTags: String?,
    val attachment: List<Attachment>,
    val endpoints: Endpoints?,
    val manuallyApprovesFollowers: Boolean?,
) {

    @Serializable
    data class Attachment(
        @SerialName("type")
        val type: String,
        @SerialName("name")
        val name: String,
        // HTMLが入ってる事がある
        @SerialName("value")
        val value: String,
    )

    @Serializable
    data class Image(
        @SerialName("mediaType")
        val mediaType: String,
        @SerialName("url")
        val url: String,
    ) {
        @SerialName("type")
        private val type: String = "Image"
    }

    @Serializable
    data class Endpoints(
        @SerialName("sharedInbox")
        val sharedInbox: String,
    )

    companion object {
        fun parse(jsonObject: JsonObject): ActivityStreamReceivePerson {
            val wrapper = JsonObjectWrapper(jsonObject)
            return ActivityStreamReceivePerson(
                context = buildList {
                    val raw = wrapper.jsonObject["@context"]!!

                    fun parseJsonElement(element: JsonElement): ActivityPubContext? {
                        return when (element) {
                            is JsonArray -> {
                                CustomLogger.ParseFail.log(
                                    ActivityStreamReceivePerson::class,
                                    buildString {
                                        appendLine("JsonArray not defined $element")
                                        appendLine(raw.toString())
                                    }
                                )
                                null
                            }

                            is JsonObject -> {
                                ActivityPubContext.Object(
                                    manuallyApprovesFollowers = element["manuallyApprovesFollowers"]?.toString(),
                                    toot = element["toot"]?.toString()
                                )
                            }

                            is JsonPrimitive -> {
                                ActivityPubContext.String(element.content)
                            }

                            JsonNull -> {
                                CustomLogger.ParseFail.log(
                                    ActivityStreamReceivePerson::class,
                                    buildString {
                                        appendLine("JsonNull is not defined $element")
                                        appendLine(raw.toString())
                                    }
                                )
                                null
                            }
                        }
                    }
                    when (raw) {
                        is JsonArray -> {
                            addAll(
                                raw.mapNotNull {
                                    parseJsonElement(it)
                                }
                            )
                        }

                        is JsonObject,
                        is JsonPrimitive -> parseJsonElement(raw)?.also { add(it) }

                        JsonNull -> Unit
                    }
                },
                type = wrapper.string("type"),
                url = wrapper.string("url"),
                name = wrapper.string("name"),
                preferredUsername = wrapper.string("preferredUsername"),
                summary = wrapper.stringOrNull("summary"),
                inbox = wrapper.string("inbox"),
                outbox = wrapper.string("outbox"),
                icon = wrapper.parseOrNull("icon"),
                image = wrapper.parseOrNull("image"),
                id = wrapper.string("id"),
                following = wrapper.stringOrNull("following"),
                followers = wrapper.stringOrNull("followers"),
                featured = wrapper.stringOrNull("featured"),
                featuredTags = wrapper.stringOrNull("featuredTags"),
                attachment = wrapper.parseOrNull<List<Attachment>>("attachment") ?: listOf(),
                endpoints = wrapper.parseOrNull("endpoints"),
                manuallyApprovesFollowers = wrapper.boolOrNull("manuallyApprovesFollowers")
            )
        }
    }
}

sealed interface ActivityPubContext {
    data class String(val value: kotlin.String) : ActivityPubContext
    data class Object(
        val manuallyApprovesFollowers: kotlin.String?,
        val toot: kotlin.String?,
    ) : ActivityPubContext
}
