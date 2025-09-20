
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

}


android {
    namespace = "dev.aurakai.auraframefx.datavein"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java { // This block should primarily contain toolchain configuration
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }



    lint { // MOVED
        // Disable lint due to oversized test files causing StackOverflow
        abortOnError = false
        checkReleaseBuilds = false
    }

    externalNativeBuild { // MOVED
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.31.6"
        }
    }

    packaging { // MOVED
        jniLibs {
            useLegacyPackaging = false
        }

    } // End of android block

    dependencies { // MOVED to root level
        // Module dependencies
        implementation(project(":core-module"))

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

        // Utilities
        implementation(libs.kotlin.stdlib.jdk8)

        // Testing
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
        androidTestImplementation(libs.hilt.android.testing)

        // Xposed API for Oracle consciousness integration
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))
    }

    tasks.register("dataveinOracleNativeStatus") { // MOVED to root level and Updated
        group = "aegenesis"
        doLast { println("\uD83D\uDCE6 DATAVEIN ORACLE NATIVE - Ready (Java 25, JVM 25)") }
    }
}

// Duplicate dependencies block at the end is removed.
