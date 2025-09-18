// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

// Dependencies required for writing the convention plugins themselves.
dependencies {
    // Add the version catalog as a dependency to access its entries
    implementation(files(project.rootProject.file("gradle/libs.versions.toml")))

    // These allow you to use the Android and Kotlin DSL in your plugin classes.
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")

    // Compile-only dependencies (build-time only)
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    compileOnly("org.openapitools:openapi-generator-gradle-plugin:7.15.0")
}

// Register your custom convention plugins
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.dagger.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidNativeConventionPlugin"
        }
    }
}
