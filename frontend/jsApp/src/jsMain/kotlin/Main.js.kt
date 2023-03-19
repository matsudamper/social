import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import kotlinx.browser.window
import navigation.NavController
import navigation.Screen
import net.matsudamper.social.common.LoginScreen
import net.matsudamper.social.common.Root
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement


fun main(args: Array<String>) {
    onWasmReady {
        @Suppress("unused")
        val canvas = (window.document.getElementById("ComposeTarget") as HTMLCanvasElement).apply {
            fillViewportSize()
        }

        Window(
            title = "social",
        ) {
            val navController = remember {
                NavController(
                    initial = Screen.Root,
                    directions = Screen.values().toList()
                )
            }

            when (val current = navController.currentNavigation) {
                Screen.Root -> {
                    Root(
                        text = current.title,
                        onClick = {
                            navController.navigate(Screen.Login)
                        }
                    )
                }

                Screen.Login -> {
                    LoginScreen()
                }
            }
        }
    }
}

private fun HTMLCanvasElement.fillViewportSize() {
    setAttribute("width", "${window.innerWidth}")
    setAttribute("height", "${window.innerHeight}")
}