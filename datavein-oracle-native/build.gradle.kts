plugins {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
=======
=======
>>>>>>> Stashed changes
    id("com.android.library")
    alias(libs.plugins.ksp)
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
    // Re-adding native plugin with exact version
>>>>>>> Stashed changes
}

android {
    namespace = "dev.aurakai.auraframefx.datavein"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildFeatures {
        compose = true
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
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

tasks.register("dataveinStatus") {
    group = "genesis"
    doLast {
        println("📊 DATAVEIN ORACLE NATIVE - ${android.namespace} - Ready!")
    }
}

tasks.register("dataveinOracleNativeStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 DATAVEIN ORACLE NATIVE - Ready (Java 24)") }
}

tasks.register("dataveinOracleNativeStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 DATAVEIN ORACLE NATIVE - Ready (Java 24)") }
}
