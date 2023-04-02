package net.matsudamper.social.frontend.common.viewmodel.admin

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.apollographql.apollo3.ApolloClient
import net.matsudamper.social.frontend.common.uistate.AdminRootScreenUiState
import net.matsudamper.social.frontend.graphql.AdminLoginMutation
import net.matsudamper.social.frontend.graphql.type.GraphQLBoolean

public class AdminScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val adminSessionStorage: AdminSessionStorage,
) {
    private val viewModelStateFlow = MutableStateFlow(ViewModelState())
    public val uiStateFlow: StateFlow<AdminRootScreenUiState> = MutableStateFlow(
        AdminRootScreenUiState(
            screen = AdminRootScreenUiState.Screen.Login(
                password = TextFieldValue(),
                onChangePassword = { },
                onClickLogin = { },
            ),
        ),
    ).also { uiStateFlow ->
        coroutineScope.launch {
            viewModelStateFlow.collect {
                uiStateFlow.update { uiState ->
                    uiState.copy(
                        screen = AdminRootScreenUiState.Screen.Login(
                            password = it.password,
                            onChangePassword = { viewModelStateFlow.update { state -> state.copy(password = it) } },
                            onClickLogin = {
                                login(it.password.text)
                            },
                        ),
                    )
                }
            }
        }
    }.asStateFlow()

    init {
        val session = adminSessionStorage.readSession()
    }

    private fun login(password: String) {
        println("password=$password")
        coroutineScope.launch {
            ApolloClient.Builder()
                .serverUrl("https://social.matsudamper.net/query")
                .build()
                .mutation(
                    AdminLoginMutation(
                        password = password,
                    ),
                )
                .execute()
                .data?.let { data ->
                    println("data=$data")
                }
        }
    }

    private data class ViewModelState(
        val password: TextFieldValue = TextFieldValue(),
    )
}
