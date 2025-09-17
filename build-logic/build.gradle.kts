// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
    // Don't apply the Hilt plugin here, we'll use it from the version catalog
}

// Configure the Hilt Gradle plugin
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    
    dependencies {
        // This makes the Hilt Gradle plugin available in the buildscript classpath
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo1.maven.org/maven2") } // For OpenAPI Generator
}

// Dependencies required for writing the convention plugins themselves.
dependencies {
    // These allow you to use the Android and Kotlin DSL in your plugin classes.
    implementation("com.android.tools.build:gradle:9.0.0-alpha02")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")

    // Hilt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.1")

    // KSP
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")

    // Compile-only dependencies (build-time only)
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
