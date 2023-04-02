package net.matsudamper.social.backend.graphql

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import graphql.schema.DataFetchingEnvironment
import me.retty.graphql.model.AdminMutationResolver
import me.retty.graphql.model.QlAdminLoginResult
import me.retty.graphql.model.QlAdminMutation
import net.matsudamper.social.backend.base.ServerEnv

class AdminMutationResolverImpl(
    private val setCookie: (key: String, value: String) -> Unit,
) : AdminMutationResolver {
    override fun addUser(
        adminMutation: QlAdminMutation,
        name: String,
        password: String,
        env: DataFetchingEnvironment,
    ): CompletionStage<Boolean> {
        TODO("Not yet implemented")
    }

    override fun login(
        adminMutation: QlAdminMutation,
        passsword: String,
        env: DataFetchingEnvironment,
    ): CompletionStage<QlAdminLoginResult> {
        return CompletableFuture.completedFuture(
            if (passsword == ServerEnv.adminPassword) {
                setCookie("social_admin_cookie", "test")
                QlAdminLoginResult(
                    isSuccess = true,
                    value = "test",
                )
            } else {
                QlAdminLoginResult(
                    isSuccess = false,
                    value = null,
                )
            },
        )
    }
}