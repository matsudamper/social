import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.browser.window
import lib.InputOutsideClickFocusRelease
import lib.NormalizeInputKeyCapture
import navigation.NavController
import navigation.Screen
import net.matsudamper.social.frontend.common.ui.CustomTheme
import net.matsudamper.social.frontend.common.ui.screen.Root
import net.matsudamper.social.frontend.common.ui.screen.admin.AdminRootScreen
import net.matsudamper.social.frontend.common.ui.screen.login.LoginScreen
import net.matsudamper.social.frontend.common.viewmodel.LoginScreenViewModel
import net.matsudamper.social.frontend.common.viewmodel.admin.AdminScreenViewModel
import org.jetbrains.skiko.wasm.onWasmReady


fun main(args: Array<String>) {
    onWasmReady {
        ResizableComposeWindow(
            title = "social",
        ) {
            NormalizeInputKeyCapture {
                InputOutsideClickFocusRelease {
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
                            callback = { updateSize() },
                        )
                    }
                    CustomTheme {
                        Box(
                            modifier = Modifier
                                .width(width)
                                .height(height)
                                .fillMaxSize(),
                        ) {
                            val navController = remember {
                                NavController(
                                    initial = Screen.Root,
                                    directions = Screen.values().toList(),
                                )
                            }

                            when (val current = navController.currentNavigation) {
                                Screen.Root -> {
                                    Root(
                                        text = current.title,
                                        onClick = {
                                            navController.navigate(Screen.Login)
                                        },
                                    )
                                }

                                Screen.Login -> {
                                    val coroutineScope = rememberCoroutineScope()
                                    val viewModel = remember {
                                        LoginScreenViewModel(
                                            coroutineScope = coroutineScope,
                                        )
                                    }
                                    LoginScreen(
                                        uiState = viewModel.uiStateFlow.collectAsState().value,
                                    )
                                }

                                Screen.Admin -> {
                                    val coroutineScope = rememberCoroutineScope()
                                    val viewModel = remember {
                                        AdminScreenViewModel(
                                            coroutineScope = coroutineScope,
                                            adminSessionStorage = JsAdminSessionStorage(),
                                        )
                                    }
                                    AdminRootScreen(
                                        uiState = viewModel.uiStateFlow.collectAsState().value,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
