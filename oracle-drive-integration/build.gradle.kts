plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "dev.aurakai.auraframefx.oracledriveintegration"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
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
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))
    implementation(project(":secure-comm"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.security.crypto)
    
    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    
    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
    
    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    
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
    implementation(libs.kotlin.reflect)
    
    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    
    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    
    // Debug tools
    debugImplementation(libs.leakcanary.android)
    
    // External libraries
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
}

tasks.register("oracleDriveIntegrationStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 ORACLE DRIVE INTEGRATION MODULE - Ready (Java 24)") }
}
