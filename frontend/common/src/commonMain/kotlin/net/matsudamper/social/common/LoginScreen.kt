package net.matsudamper.social.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.matsudamper.social.common.util.ScreenSizeMode

@Composable
public fun LoginScreen() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(Color.Cyan)
    ) {
        val width = this.maxWidth
        val height = this.maxHeight

        val mode = remember(width) {
            ScreenSizeMode.from(width)
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "$width"
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "$width"
            )
            when (mode) {
                ScreenSizeMode.Mobile -> {
                    BasicTextField(
                        value = "mobile",
                        onValueChange = {},
                    )
                }

                ScreenSizeMode.Desktop -> {
                    Text("desktop")
                }
            }
        }
    }
}