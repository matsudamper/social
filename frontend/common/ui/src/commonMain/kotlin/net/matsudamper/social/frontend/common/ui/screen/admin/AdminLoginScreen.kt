package net.matsudamper.social.frontend.common.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.matsudamper.social.frontend.common.ui.CustomColors
import net.matsudamper.social.frontend.common.uistate.AdminRootScreenUiState

@Composable
public fun AdminLoginScreen(
    uiState: AdminRootScreenUiState.Screen.Login,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(max = 500.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Column(
                Modifier
                    .padding(16.dp),
            ) {
                Text("管理画面")
                Spacer(Modifier.height(24.dp))
                TextField(
                    value = uiState.password,
                    placeholder = {
                        Text("password")
                    },
                    onValueChange = {
                        uiState.onChangePassword(it)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                    ),
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { uiState.onClickLogin() },
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}
