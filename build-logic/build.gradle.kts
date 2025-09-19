// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo1.maven.org/maven2") } // For OpenAPI Generator
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
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.51")

    // KSP
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.0-1.0.21")

    // Compose Compiler
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.5.12")

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
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24

    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

// Configure Kotlin compilation for all tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
        freeCompilerArgs.addAll(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlin.ExperimentalStdlibApi"
        )
    }
}

// Configure Java compilation for all tasks
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
    options.isIncremental = true
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

        register("statusTask