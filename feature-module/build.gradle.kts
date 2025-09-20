// ==== GENESIS PROTOCOL - FEATURE MODULE ====
// Primary feature module using convention plugins


plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

}


android {
    namespace = "dev.aurakai.auraframefx.feature"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    // Configure Java compilation
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }



    dependencies { // MOVED to root level
        // Module dependencies
        api(project(":core-module"))

        // Core Android
        implementation(libs.androidx.core.ktx)
        implementation(libs.bundles.lifecycle)

        // Compose
        val composeBom = platform(libs.androidx.compose.bom)
        implementation(composeBom)
        androidTestImplementation(composeBom)
        implementation(libs.bundles.compose.ui)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        debugImplementation(libs.bundles.compose.debug)
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Coroutines
        implementation(libs.bundles.coroutines)

        // Networking
        implementation(libs.bundles.network)
        implementation(libs.gson)

        // Room Database
        implementation(libs.bundles.room)
        // implementation(libs.hilt.android) // Hilt already included above

        // Firebase
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.messaging)
        implementation(libs.firebase.config)
        implementation(libs.firebase.database)
        implementation(libs.firebase.storage)

        // Utilities
        implementation(libs.timber)
        implementation(libs.coil.compose)
        implementation(libs.kotlin.stdlib.jdk8)

        // External libraries
        implementation(fileTree("../Libs") { include("*.jar") })

        // Testing
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
        androidTestImplementation(libs.hilt.android.testing)

        // Debug tools
        debugImplementation(libs.leakcanary.android)
    }

    tasks.register("featureStatus") { // MOVED to root level and Updated
        group = "aegenesis"
        doLast { println("🚀 FEATURE MODULE - ${android.namespace} - Ready (Java 25, JVM 25)!") }
    }
}

// Duplicate dependencies block at the end is removed.
