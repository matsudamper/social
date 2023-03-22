import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import kotlinx.browser.window
import navigation.NavController
import navigation.Screen
import net.matsudamper.social.common.LoginScreen
import net.matsudamper.social.common.Root
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement


fun main(args: Array<String>) {
    @Suppress("unused")
    val canvas = (window.document.getElementById("ComposeTarget") as HTMLCanvasElement).apply {
        fillViewportSize()
    }

    onWasmReady {
        ResizableComposeWindow(
            title = "social",
        ) {
            val density = LocalDensity.current
            var width by remember { mutableStateOf(0.dp) }
            var height by remember { mutableStateOf(0.dp) }
            fun updateSize() {
                width = with(density) {
                    window.innerWidth.toDp() * density.density
                }
                height = with(density) {
                    window.innerHeight.toDp() * density.density
                }
                println("updateSize: ($width, $height)")
            }
            LaunchedEffect(density) {
                updateSize()
                window.addEventListener(
                    type = "resize",
                    callback = { updateSize() }
                )
            }
            Box(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .fillMaxSize()
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
}

private fun HTMLCanvasElement.fillViewportSize() {
    setAttribute("width", "${window.innerWidth * 1}")
    setAttribute("height", "${window.innerHeight * 1}")
}