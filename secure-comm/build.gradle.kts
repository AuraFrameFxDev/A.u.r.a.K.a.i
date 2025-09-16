// ==== GENESIS PROTOCOL - SECURE COMMUNICATION MODULE ====
// Security module using convention plugins

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)

}

android {
    namespace = "dev.aurakai.auraframefx.securecomm"
    compileSdk = 36

    defaultConfig {
        minSdk = 34

        // For testing and linting
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
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
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
    implementation(project(":core-module"))
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // Security - BouncyCastle for cryptography
    implementation(libs.bcprov.jdk18on)
    
    // Add other module-specific dependencies here
    implementation(libs.kotlin.stdlib.jdk8)

    // Test dependencies
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
}
