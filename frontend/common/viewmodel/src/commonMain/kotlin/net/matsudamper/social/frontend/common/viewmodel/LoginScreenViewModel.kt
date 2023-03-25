package net.matsudamper.social.frontend.common.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.matsudamper.social.frontend.common.uistate.LoginScreenUiState

public class LoginScreenViewModel(
    private val coroutineScope: CoroutineScope,
) {
    private val viewModelStateFlow: MutableStateFlow<ViewModelState> = MutableStateFlow(
        ViewModelState(),
    )
    public val uiStateFlow: StateFlow<LoginScreenUiState> = MutableStateFlow(
        LoginScreenUiState(
            userName = TextFieldValue(),
            password = TextFieldValue(),
            listener = object : LoginScreenUiState.Listener {
                override fun onPasswordChange(value: TextFieldValue) {
                    viewModelStateFlow.update {
                        it.copy(password = value)
                    }
                }

                override fun onUserNameChange(value: TextFieldValue) {
                    viewModelStateFlow.update {
                        it.copy(userName = value)
                    }
                }

                override fun onClickLogin() {
                    TODO("Not yet implemented")
                }
            },
        ),
    ).also { uiStateFlow ->
        coroutineScope.launch {
            viewModelStateFlow.collect { viewModelState ->
                uiStateFlow.update { uiState ->
                    uiState.copy(
                        userName = viewModelState.userName,
                        password = viewModelState.password,
                    )
                }
            }
        }
    }.asStateFlow()

    private data class ViewModelState(
        val userName: TextFieldValue = TextFieldValue(),
        val password: TextFieldValue = TextFieldValue(),
    )
}
