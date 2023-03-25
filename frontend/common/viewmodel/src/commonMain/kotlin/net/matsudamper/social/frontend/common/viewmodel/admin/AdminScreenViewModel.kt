package net.matsudamper.social.frontend.common.viewmodel.admin

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.matsudamper.social.frontend.common.uistate.AdminRootScreenUiState

public class AdminScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val adminSessionStorage: AdminSessionStorage,
) {
    public val uiStateFlow: StateFlow<AdminRootScreenUiState> = MutableStateFlow(
        AdminRootScreenUiState(
            screen = AdminRootScreenUiState.Screen.Login(
                password = TextFieldValue(),
                onChangePassword = { },
                onClickLogin = { },
            ),
        ),
    ).also { stateFlow ->
//        stateFlow
    }.asStateFlow()

    init {
        val session = adminSessionStorage.readSession()
    }
}
