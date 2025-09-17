// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)

}

android {
    namespace = "dev.aurakai.screenshottests"
    compileSdk = 36

    kotlin {
        jvmToolchain(24)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        }
    }
}
