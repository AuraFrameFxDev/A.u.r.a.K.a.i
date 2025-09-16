// GENESIS PROTOCOL - MODULE F
plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.module.f"
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
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    }

    tasks.register("moduleFStatus") {
        group = "aegenesis"
        doLast { println("\uD83D\uDCE6 MODULE F - Ready (Java 24)") }
    }
}
