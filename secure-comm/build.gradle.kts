// ==== GENESIS PROTOCOL - SECURE COMMUNICATION MODULE ====
// Security module using convention plugins

plugins {
    id("genesis.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "dev.aurakai.auraframefx.securecomm"
    
    defaultConfig {
        minSdk = 34
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
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)

    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization.json)

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

    // Security - BouncyCastle for cryptography
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")
    
    // Testing
    testImplementation(libs.bundles.testing.unit)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")

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
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}


tasks.register("secureStatus") {
    group = "genesis"
    doLast {
        println("🔐 SECURE COMM - ${android.namespace} - Ready!")
    }
=======
tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
=======
tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
}
