package net.matsudamper.social.common.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class ScreenSizeMode {
    Mobile,
    Desktop,
    ;

    companion object {
        fun from(width: Dp): ScreenSizeMode {
            return if (width > 400.dp) {
                Desktop
            } else {
                Mobile
            }
        }
    }
}