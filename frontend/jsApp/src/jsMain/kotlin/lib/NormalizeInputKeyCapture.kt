package lib
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.browser.document
import org.w3c.dom.events.Event

@Composable
public fun NormalizeInputKeyCapture(content: @Composable () -> Unit) {
    var hasFocus by remember {
        mutableStateOf(false)
    }
    val callback: (Event) -> Unit = remember {
        {
            it.stopPropagation()
        }
    }
    LaunchedEffect(hasFocus) {
        val target = document.getElementById("ComposeTarget")!!
        if (hasFocus) {
            target.addEventListener(
                type = "keydown",
                callback = callback,
            )
        } else {
            target.removeEventListener(
                type = "keydown",
                callback = callback,
            )
        }
    }

    Box(
        modifier = Modifier
            .onFocusChanged {
                hasFocus = it.hasFocus
            },
    ) {
        content()
    }
}
