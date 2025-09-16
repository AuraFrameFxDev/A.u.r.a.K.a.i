// ==== GENESIS PROTOCOL - FEATURE MODULE ====
// Primary feature module using convention plugins

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
>>>>>>> Stashed changes
}

android {
    namespace = "dev.aurakai.auraframefx.feature"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    // Configure Java compilation
    compileOptions {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Configure Kotlin compilation
    kotlin {
        jvmToolchain(17)
=======
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
=======
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
>>>>>>> Stashed changes
    kotlinOptions {
        jvmTarget = "23"
    }

    // Configure Kotlin compilation using the new compilerOptions DSL
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
>>>>>>> Stashed changes
    }
}

dependencies {
    // Module dependencies
    api(project(":core-module"))
    
    // Core Android
    implementation(libs.androidx.core.ktx)
<<<<<<< Updated upstream
    implementation(libs.bundles.lifecycle)
    
    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
=======
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
<<<<<<< Updated upstream
=======
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.compose.bom)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.firebase.performance)
    implementation(libs.firebase.auth)
>>>>>>> Stashed changes
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.compose.bom)
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
tasks.register("featureStatus") {
    group = "genesis"
    doLast { 
        println("🚀 FEATURE MODULE - ${android.namespace} - Ready!")
    }
=======
tasks.register("featureModuleStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 FEATURE MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
=======
tasks.register("featureModuleStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 FEATURE MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
}
