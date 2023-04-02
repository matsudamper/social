package net.matsudamper.social.backend.graphql

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import graphql.GraphQL
import graphql.execution.AsyncExecutionStrategy
import graphql.kickstart.tools.SchemaParser
import graphql.schema.DataFetchingEnvironment
import net.matsudamper.social.graphql.model.MutationResolver
import net.matsudamper.social.graphql.model.QlAdminMutation

object SocialGraphQlSchema {
    private val schemaFiles = ClassLoader.getSystemClassLoader()
        .getResourceAsStream("graphql")!!
        .bufferedReader().lines()
        .toList()
        .onEach {
            println("lines -> $it")
        }
        .map {
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("graphql/$it")!!
                .bufferedReader()
                .readText()
        }

    init {
        println("============schema===============")
        println(schemaFiles.joinToString("\n"))
    }

    private val schema = SchemaParser.newParser()
        .schemaString(schemaFiles.joinToString("\n"))
        .resolvers(
            QueryResolverImpl(),
            object : MutationResolver {
                override fun adminMutation(env: DataFetchingEnvironment): CompletionStage<QlAdminMutation> {
                    return CompletableFuture.completedFuture(QlAdminMutation())
                }
            },
            AdminMutationResolverImpl(),
        )
        .build()
        .makeExecutableSchema()

    val graphql = GraphQL.newGraphQL(schema)
        .queryExecutionStrategy(AsyncExecutionStrategy())
        .build()
}
