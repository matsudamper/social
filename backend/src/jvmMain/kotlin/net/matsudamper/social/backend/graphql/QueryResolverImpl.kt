package net.matsudamper.social.backend.graphql

import java.util.concurrent.CompletionStage
import graphql.schema.DataFetchingEnvironment
import net.matsudamper.social.graphql.model.QueryResolver

class QueryResolverImpl : QueryResolver {
    override fun adminConfig(env: DataFetchingEnvironment): CompletionStage<String> {
        TODO("Not yet implemented")
    }
}