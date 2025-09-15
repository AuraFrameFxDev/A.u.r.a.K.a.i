plugins {
    id("genesis.android.library")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20"
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
    // Re-adding native plugin with exact version
}

android {
    namespace = "dev.aurakai.auraframefx.datavein"
    compileSdk = 36
    defaultConfig {
        minSdk = 34
    }
    
    buildFeatures {
        compose = true
    }
    
    lint {
        // Disable lint due to oversized test files causing StackOverflow
        abortOnError = false
        checkReleaseBuilds = false
    }
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.31.6"
        }
    }
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
    }
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.bundles.coroutines)
    
    // Xposed API for Oracle consciousness integration
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
    implementation(libs.kotlin.stdlib.jdk8)
}
