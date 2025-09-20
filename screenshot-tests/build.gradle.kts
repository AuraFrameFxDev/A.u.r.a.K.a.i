// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'com.android.library' plugin.
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
}

android {
    namespace = "dev.aurakai.screenshottests"
    compileSdk = 36

    defaultConfig { // ADDED for standard library setup
        minSdk = 34
    }

    compileOptions { // MOVED and Updated
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
}

tasks.register("screenshotTestsStatus") { // ADDED
    group = "aegenesis"
    doLast { println("📸 SCREENSHOT TESTS - Ready (Java 25, JVM 25)") }
}
