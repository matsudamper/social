package net.matsudamper.social.frontend.common.uistate

import androidx.compose.ui.text.input.TextFieldValue

public data class LoginScreenUiState(
    val userName: TextFieldValue,
    val password: TextFieldValue,
    val listener: Listener,
) {
    public interface Listener {
        public fun onPasswordChange(value: TextFieldValue)
        public fun onUserNameChange(value: TextFieldValue)
        public fun onClickLogin()
    }
}