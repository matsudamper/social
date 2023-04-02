import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("multiplatform")
    id("io.github.kobylynskyi.graphql.codegen") version "5.5.0"
}

val generatedPath = "$buildDir/generated/codegen"
kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        jvmToolchain(17)
        val jvmMain by getting {
            dependencies {
                implementation(project(":backend:base"))

                implementation(kotlin("stdlib"))
                implementation(libs.kotlin.serialization.json)
                implementation(libs.logback.classic)

                api("com.graphql-java-kickstart:graphql-java-tools:13.0.2")
            }

        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

sourceSets {
    named("main") {
        kotlin {
            java.setSrcDirs(
                listOf(
                    "src/main/kotlin",
                    generatedPath,
                ).map { File(it) },
            )
        }
    }
}

val graphqlCodegen = tasks.named<io.github.kobylynskyi.graphql.codegen.gradle.GraphQLCodegenGradleTask>("graphqlCodegen") {
    graphqlSchemaPaths = file("$projectDir/src/graphql").listFiles().orEmpty()
        .filter { it.extension == "graphqls" }
        .map { it.toString() }
    generatedLanguage = com.kobylynskyi.graphql.codegen.model.GeneratedLanguage.KOTLIN
    outputDir = File(generatedPath)
    packageName = "me.retty.graphql.model"
    addGeneratedAnnotation = true
    fieldsWithResolvers = setOf("@lazy")
    generateParameterizedFieldsResolvers = true
    generateBuilder = false
    apiReturnType = "java.util.concurrent.CompletionStage"
    generateDataFetchingEnvironmentArgumentInApis = true
    generateImmutableModels = true
    modelNamePrefix = "Ql"
    generateApisWithThrowsException = false
    isGenerateSealedInterfaces = true
    parentInterfaces {
        resolver = "graphql.kickstart.tools.GraphQLResolver<{{TYPE}}>"
    }
//    generateModelsForRootTypes = true
//    customTypesMapping = mutableMapOf(Pair("EpochMillis", "java.time.LocalDateTime"))
//    customAnnotationsMapping = mutableMapOf(
//        Pair(
//            "EpochMillis",
//            listOf("@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.example.json.EpochMillisScalarDeserializer.class)")
//        )
//    )
}

tasks.withType<JavaCompile>() {
    dependsOn(graphqlCodegen)
}
