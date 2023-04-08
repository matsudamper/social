import org.gradle.internal.jvm.Jvm

plugins {
    id("application")
    kotlin("multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

base.archivesName.set("social")
group = "net.matsudamper.social.backend"

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        jvmToolchain(17)
        val jvmMain by getting {
            dependencies {
                implementation(project(":backend:activitystreams"))
                implementation(project(":backend:base"))
                implementation(project(":backend:graphql"))

                implementation(kotlin("stdlib"))
                implementation(libs.kotlin.serialization.json)
                implementation(libs.logback.classic)

                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.engine)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.server.statusPages)
                implementation(libs.ktor.server.defaultHeaders)
                implementation(libs.ktor.server.fowardedHeader)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktor.server.contentNegotiation)
                implementation(libs.ktor.server.callLogging)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}


//tasks.test {
//    useJUnitPlatform()
//}
