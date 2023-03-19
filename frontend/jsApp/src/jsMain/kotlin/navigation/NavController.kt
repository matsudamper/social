package navigation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.browser.window

@Immutable
class NavController<Dir: Direction>(
    initial: Dir,
    directions: List<Dir>,
) {
    var currentNavigation by mutableStateOf<Dir>(initial)

    init {
        currentNavigation = directions.first { it.url == window.location.pathname }

        window.addEventListener("popstate", callback = {
            currentNavigation = directions.first { it.url == window.location.pathname }
        })
    }

    fun navigate(navigation: Dir) {
        window.history.pushState(
            data = null,
            title = navigation.title,
            url = navigation.url,
        )
        currentNavigation = navigation
    }
}

interface Direction {
    val title: String
    val url: String
}
