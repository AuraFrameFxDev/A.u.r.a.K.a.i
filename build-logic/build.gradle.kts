// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo1.maven.org/maven2") }
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
        name = "Gradle Releases"
    }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        name = "JetBrains Compose Dev"
    }
}

// Dependencies for the build-logic module itself
dependencies {
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.4.0")

    // Kotlin Gradle Plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.0")

    // Google Services and Firebase
    implementation("com.google.gms:google-services:4.4.2")
    implementation("com.google.firebase:firebase-crashlytics-gradle:3.0.2")

    // Hilt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.1")

    // KSP
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.0-1.0.21")

    // Compose Compiler
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.5.12")

    // OpenAPI Generator (Example dependency, adjust as needed)
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.5.0")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("com.google.truth:truth:1.1.5")

    // Kotlin DSL support
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
}

// Configure Java toolchain for build-logic module
java {
    sourceCompatibility = JavaVersion.VERSION_21 // UPDATED
    targetCompatibility = JavaVersion.VERSION_21 // UPDATED
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

// ADDED this block:
kotlin {
    jvmToolchain(21)
}
