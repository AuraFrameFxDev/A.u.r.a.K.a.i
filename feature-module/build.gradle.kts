// ==== GENESIS PROTOCOL - FEATURE MODULE ====
// Primary feature module using convention plugins

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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
}

dependencies {
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
    
    // Debug Compose
    debugImplementation(libs.bundles.compose.debug)
    
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
    ksp(libs.androidx.room.compiler)
    
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database.connection.license)
    
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
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    
    // Debug tools
    debugImplementation(libs.leakcanary.android)
}

tasks.register("featureModuleStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 FEATURE MODULE - Ready (Java 24)") }
}
