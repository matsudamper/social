package net.matsudamper.social.frontend.common.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver

@Composable
public fun CustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xff8BC34A),
            onPrimary = Color.White,//Color(0xff444654),
            background = CustomColors.backgroundColor,
            surface = CustomColors.surfaceColor,
            onSurface = Color.White,
            onSecondary = Color.Green,
            isLight = false,
        ),
    ) {
        CompositionLocalProvider(
            LocalFontFamilyResolver provides remember {
                createFontFamilyResolver()
            },
        ) {
            content()
        }
    }
}
