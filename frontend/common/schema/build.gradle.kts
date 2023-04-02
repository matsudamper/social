plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("com.apollographql.apollo3").version("3.7.5")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.apollographql.apollo3:apollo-runtime:3.7.5")
            }
        }
    }
}

apollo {
    service("social") {
        packageName.set("net.matsudamper.social.frontend.graphql")
        schemaFiles.setFrom(
            file("$rootDir/backend/graphql/src/graphql").listFiles(),
        )
    }
}

tasks.getByName("compileKotlinJs") {
    dependsOn("generateApolloSources")
    dependsOn("generateSocialApolloSchema")
}
