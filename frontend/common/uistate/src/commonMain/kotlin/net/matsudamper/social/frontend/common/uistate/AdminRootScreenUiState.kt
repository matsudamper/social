package net.matsudamper.social.frontend.common.uistate

import androidx.compose.ui.text.input.TextFieldValue

public data class AdminRootScreenUiState(
    val screen: Screen,
) {
    public sealed interface Screen {
        public data class Login(
            val password: TextFieldValue,
            val onChangePassword: (TextFieldValue) -> Unit,
            val onClickLogin: () -> Unit,
        ) : Screen

        public class Admin: Screen
    }
}
