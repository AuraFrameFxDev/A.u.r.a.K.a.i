// Apply plugins (versions via version catalog)
plugins {
    id("genesis.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
}

android {
    namespace = "dev.aurakai.auraframefx.collabcanvas" +
            defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // For test builds
    testOptions {
        targetSdk = 36
    }

    // For linting
    lint {
        targetSdk = 36
    }
    
    buildFeatures {
        buildConfig = true
        // compose = true  // Removed as per user's request to handle compose separately
    }

    // Compose options
    id("com.android.library")
    alias(libs.plugins.ksp) composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
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
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.bundles.compose.debug)

    // Firebase
    implementation(platform(libs.firebase.bom))

    // Networking
    implementation(libs.bundles.network)
    implementation(libs.gson)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // UI / Utils
    implementation(libs.coil.compose)
    implementation(libs.timber)
    implementation(libs.kotlin.stdlib.jdk8)
    
    // External libraries
    implementation(fileTree("../Libs") { include("*.jar") })
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}

tasks.register("collabStatus") {
    group = "genesis"
    doLast {
        println("🎨 COLLAB CANVAS - ${android.namespace} - Ready!")
    }
}
