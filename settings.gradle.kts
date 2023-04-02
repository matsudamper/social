@file:Suppress("UnstableApiUsage")

rootProject.name = "social"
include(":backend:activitystreams")
include(":backend:base")
include(":backend:graphql")
include(":backend")

include(":frontend:jsApp")
include(":frontend:common:ui")
include(":frontend:common:viewmodel")
include(":frontend:common:uistate")
include(":frontend:common:schema")

include(":shared")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    @Suppress("UnstableApiUsage")
    versionCatalogs {
        create("libs") {
            val kotlinVersion = extra["kotlin.version"] as String
//            plugin("kotlin", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
            plugin("kotlin.multiplatform", "org.jetbrains.kotlin.multiplatform").version(kotlinVersion)
            plugin("kotlin.serialization", "org.jetbrains.kotlin.plugin.serialization").version(kotlinVersion)

            library("kotlin.serialization.json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

            library("log4j.api","org.slf4j:slf4j-api:2.0.6")
            library("logback.classic","ch.qos.logback:logback-classic:1.4.5")

            val ktorVersion = "2.2.4"
            library("ktor.server.core", "io.ktor:ktor-server-core:$ktorVersion")
            library("ktor.server.engine", "io.ktor:ktor-server-cio:$ktorVersion")
            library("ktor.server.statusPages", "io.ktor:ktor-server-status-pages:$ktorVersion")
            library("ktor.server.defaultHeaders", "io.ktor:ktor-server-default-headers:$ktorVersion")
            library("ktor.serialization.json", "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            library("ktor.server.contentNegotiation", "io.ktor:ktor-server-content-negotiation:$ktorVersion")
            library("ktor.client.core", "io.ktor:ktor-client-core:$ktorVersion")
            library("ktor.client.cio", "io.ktor:ktor-client-cio:$ktorVersion")
        }
    }
}
