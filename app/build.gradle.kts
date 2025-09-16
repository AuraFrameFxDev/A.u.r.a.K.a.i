// ==== GENESIS PROTOCOL - MAIN APPLICATION ====
// This build script now uses the custom convention plugins for a cleaner setup.

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    id("org.openapi.generator") version "7.15.0"
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Build features
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    // Build types
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // This adds the generated OpenAPI source code to the main source set.
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Kotlin compiler options
    kotlin {
        jvmToolchain(17)
    }
}

tasks.openApiGenerate {
    generatorName.set("kotlin")
    // Use the correct file URI for the OpenAPI spec
    inputSpec.set("${project.projectDir}/api/system-api.yml")
    outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.absolutePath)
    apiPackage.set("dev.aurakai.auraframefx.openapi.api")
    modelPackage.set("dev.aurakai.auraframefx.openapi.model")
    invokerPackage.set("dev.aurakai.auraframefx.openapi.invoker")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "library" to "jvm-ktor"
        )
    )
}

dependencies {
    // ===== MODULE DEPENDENCIES =====
    implementation(project(":core-module"))
    implementation(project(":feature-module"))
    implementation(project(":oracle-drive-integration"))
    implementation(project(":romtools"))
    implementation(project(":secure-comm"))
    implementation(project(":collab-canvas"))
    implementation(project(":colorblendr"))
    implementation(project(":sandbox-ui"))
    implementation(project(":datavein-oracle-native"))

    // Compose & AndroidX
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.bundles.room)
    
    // Compose Debug
    debugImplementation(libs.bundles.compose.debug)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Networking & Serialization
    implementation(libs.bundles.network)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database.connection.license)

    // Utilities & Logging
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.androidx.security.crypto)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Debugging
    debugImplementation(libs.leakcanary.android)

    // XPOSED/LSPosed Integration
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // Kotlin libs
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.reflect)
}
