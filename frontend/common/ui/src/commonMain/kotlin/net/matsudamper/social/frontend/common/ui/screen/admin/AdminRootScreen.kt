package net.matsudamper.social.frontend.common.ui.screen.admin

import androidx.compose.runtime.Composable
import net.matsudamper.social.frontend.common.uistate.AdminRootScreenUiState


@Composable
public fun AdminRootScreen(
    uiState: AdminRootScreenUiState,
) {
    when (val screen = uiState.screen) {
        is AdminRootScreenUiState.Screen.Login -> {
            AdminLoginScreen(
                uiState = screen,
            )
        }

        is AdminRootScreenUiState.Screen.Admin -> {
            AdminScreen(
                uiState = screen
            )
        }
    }
}
