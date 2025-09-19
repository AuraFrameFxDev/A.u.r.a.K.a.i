// ==== GENESIS PROTOCOL - COLORBLENDR MODULE ====
// Color utility and theming module
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)  // 🔥 CRITICAL: Add Compose Compiler Plugin
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "dev.aurakai.auraframefx.colorblendr"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    // Modern toolchain configuration
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.lifecycle)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation(libs.mockk)
    testImplementation(kotlin("test"))

    // Android Testing
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")

    // Hilt Testing
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Utilities
    implementation(libs.timber)
    implementation(libs.kotlin.stdlib.jdk8)
}

// Move the tasks registration block outside the dependencies block
tasks.register("colorblendrStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24)") }
}
