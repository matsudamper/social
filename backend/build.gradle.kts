plugins {
    id("application")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

base.archivesName.set("social")
group = "net.matsudamper.social"

dependencies {
    implementation(project(":activitystreams"))
    implementation(project(":base"))

    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.serialization.json)
    implementation(libs.logback.classic)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.engine)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.defaultHeaders)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.server.contentNegotiation)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {

    }
}