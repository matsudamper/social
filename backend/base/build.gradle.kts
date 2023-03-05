plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(libs.kotlin.serialization.json)
    implementation(libs.log4j.api)
}

tasks.test {
    useJUnitPlatform()
}