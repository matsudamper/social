import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import kotlinx.browser.window
import kotlinx.coroutines.flow.MutableStateFlow
import net.matsudamper.social.common.Root
import org.jetbrains.skiko.wasm.onWasmReady

@Immutable
class NavController {
    var path: MutableStateFlow<String> = MutableStateFlow(window.location.pathname)
    init {
        window.addEventListener("popstate", callback = {
            path.value = window.location.pathname
        })
    }

    fun navigateLogin() {
        val url = "/login"
        window.history.pushState(
            data = null,
            title = Title.Login.titleName,
            url = url,
        )
        path.value = url
    }

    enum class Title(val titleName: String) {
        Login("ログイン"),
    }
}

fun main(args: Array<String>) {
    onWasmReady {
        window.history
        Window("Falling Balls") {
            val navController = remember { NavController() }

            Root(
                text = navController.path.collectAsState().value,
                onClick = {
                    navController.navigateLogin()
                }
            )
        }
    }
}
