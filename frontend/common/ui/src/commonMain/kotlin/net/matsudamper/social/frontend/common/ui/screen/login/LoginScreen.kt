package net.matsudamper.social.frontend.common.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.matsudamper.social.frontend.common.ui.CustomColors
import net.matsudamper.social.frontend.common.uistate.LoginScreenUiState


@Composable
public fun LoginScreen(
    uiState: LoginScreenUiState,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(CustomColors.backgroundColor),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(max = 500.dp),
        ) {
            TextField(
                value = uiState.userName,
                onValueChange = {
                    uiState.listener.onUserNameChange(it)
                },
                placeholder = {
                    Text("user name")
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.password,
                onValueChange = {
                    uiState.listener.onPasswordChange(it)
                },
                placeholder = {
                    Text("password")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                ),
            )
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { uiState.listener.onClickLogin() },
            ) {
                Text(text = "Login")
            }
        }
    }
}
