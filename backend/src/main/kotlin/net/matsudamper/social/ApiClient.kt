package net.matsudamper.social

import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.util.logging.error
import net.matsudamper.social.activitystreams.ActivityStreamReceivePerson
import net.matsudamper.social.base.ObjectMapper
import org.slf4j.LoggerFactory
import java.net.URL
import net.matsudamper.social.base.CustomLogger


class ApiClient(
    private val httpClient: HttpClient = HttpClient(CIO),
) {

    suspend fun getPersonJson(url: String) : String {
        val body = httpClient
            .get {
                url(URL(url))
                header("Accept", "application/activity+json")
            }
            .call.body<String>()
        CustomLogger.General.debug(body)
        return body
    }

    suspend fun getPerson(url: String) : Result<ActivityStreamReceivePerson> {
        val body = httpClient
            .get {
                url(URL(url))
                header("Accept", "application/activity+json")
            }
            .call.body<String>()
        CustomLogger.General.debug(body)
        return runCatching {
            val jsonElement = ObjectMapper.json
                .decodeFromString<JsonObject>(body)
            ActivityStreamReceivePerson.parse(jsonElement)
        }.onFailure {
            LoggerFactory.getLogger("").error(it)
        }
    }
}