plugins {
//    alias(libs.plugins.kotlin)
    kotlin("multiplatform") apply false
    alias(libs.plugins.kotlin.serialization) apply false
    id("org.jetbrains.compose") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

dependencies {
//    kotlin {
//        sourceSets {
//            val commonMain by getting {
////                implementation(project(":backend"))
//            }
//        }
//    }
}

//tasks.test {
//    useJUnitPlatform()
//}

//kotlin {
//    jvmToolchain(17)
//}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {

    }
}