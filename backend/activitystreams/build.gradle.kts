plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm {
        withJava()
    }
    jvmToolchain(17)
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":backend:base"))

                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation(libs.kotlin.serialization.json)
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