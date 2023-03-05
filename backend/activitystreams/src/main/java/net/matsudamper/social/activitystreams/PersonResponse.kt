package net.matsudamper.social.activitystreams

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    @SerialName("@context") val context: List<String>,
    @SerialName("inbox") val inbox: String,
    @SerialName("outbox") val outbox: String,
    @SerialName("url") val url: String,
    @SerialName("name") val name: String,
    @SerialName("preferredUsername") val preferredUsername: String,
    @SerialName("summary") val summary: String,
) {
    @SerialName("id")
    @Suppress("unused")
    val id: String = url

    @SerialName("type")
    @Suppress("unused")
    val type: String = "Person"
}
