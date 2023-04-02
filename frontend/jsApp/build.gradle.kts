plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":frontend:common:ui"))
                implementation(project(":frontend:common:uistate"))
                implementation(project(":frontend:common:viewmodel"))
                implementation(project(":frontend:common:schema"))
                implementation(kotlin("stdlib"))
                implementation(libs.kotlin.serialization.json)
                implementation("com.apollographql.apollo3:apollo-runtime:3.7.5")
            }
        }
    }
}


afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
//        versions.webpackCli.version = "5.0.1"
    }
}

compose.experimental {
    web.application {}
}
