// GENESIS PROTOCOL - MODULES A-F
// Module E
plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.module.e"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        }
    }


    dependencies {
        implementation(project(":core-module"))
        implementation(libs.androidx.core.ktx)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Add other module-specific dependencies here
        implementation(libs.kotlin.stdlib.jdk8)
    }

    tasks.register("moduleEStatus") {
        group = "aegenesis"
        doLast { println("📦 MODULE E - Ready (Java 24)") }
    }
}
