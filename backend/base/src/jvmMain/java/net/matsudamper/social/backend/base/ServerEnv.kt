package net.matsudamper.social.backend.base

object ServerEnv {
    val domain = System.getenv()["SOCIAL_DOMAIN"]
    val port = System.getenv()["SOCIAL_PORT"]?.toIntOrNull() ?: 80
    val frontPath = System.getenv()["HTML_PATH"] ?: "./frontend/jsApp/build/distributions"
    val htmlPath = "$frontPath/index.html"

    val adminPassword = System.getenv()["ADMIN_PASSWORD"]

    object Wasm {
        val name = "skiko.wasm"
        val internalPath = "$frontPath/skiko.wasm"
    }

    object Skikko {
        val name = "skiko.js"
        val internalPath = "$frontPath/skiko.js"
    }

    object Css {
        val name = "styles.css"
        val internalPath = "$frontPath/styles.css"
    }

    object JsApp {
        val name = "jsApp.js"
        val internalPath = "$frontPath/jsApp.js"
    }
    object JsAppMap {
        val name = "jsApp.js.map"
        val internalPath = "$frontPath/jsApp.js.map"
    }
}
