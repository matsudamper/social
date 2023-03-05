plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":base"))

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(libs.kotlin.serialization.json)
}

tasks.test {
    useJUnitPlatform()
}