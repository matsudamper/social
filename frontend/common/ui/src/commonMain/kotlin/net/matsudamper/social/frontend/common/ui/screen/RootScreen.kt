package net.matsudamper.social.frontend.common.ui.screen

import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
public fun Root(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick = {
        onClick()
    }) {
        Text(text)
    }
}
