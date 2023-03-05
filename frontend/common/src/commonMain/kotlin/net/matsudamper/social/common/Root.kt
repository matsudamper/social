package net.matsudamper.social.common

import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun Root(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick = {
        onClick()
    }) {
        Text(text)
    }
}
