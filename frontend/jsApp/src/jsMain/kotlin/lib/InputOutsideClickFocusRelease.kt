package lib

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
public fun InputOutsideClickFocusRelease(
    content: @Composable () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier.focusRequester(focusRequester)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { focusRequester.freeFocus() },
    ) {
        content()
    }
}