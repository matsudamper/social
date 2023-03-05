package net.matsudamper.social.base

object ServerEnv {
    val domain = System.getenv()["SOCIAL_DOMAIN"]
    val frontPath = System.getenv()["HTML_PATH"] ?: "../frontend/jsApp/build/distributions"
    val htmlPath = "$frontPath/index.html"

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
