package net.matsudamper.social.backend.graphql

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import graphql.schema.DataFetchingEnvironment
import net.matsudamper.social.backend.SocialGraphQlContext
import net.matsudamper.social.backend.base.ServerEnv
import net.matsudamper.social.graphql.model.AdminMutationResolver
import net.matsudamper.social.graphql.model.QlAdminLoginResult
import net.matsudamper.social.graphql.model.QlAdminMutation

class AdminMutationResolverImpl : AdminMutationResolver {
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
        val context = env.getLocalContext<SocialGraphQlContext>()
        return CompletableFuture.completedFuture(
            if (passsword == ServerEnv.adminPassword) {
                context.setCookie("social_admin_cookie", "test")
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