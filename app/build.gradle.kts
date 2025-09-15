// ==== GENESIS PROTOCOL - MAIN APPLICATION ====
// This build script now uses the custom convention plugins for a cleaner setup.

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.compose") version "1.8.2"
    id("org.openapi.generator") version "7.15.0"
    
    // Firebase plugins
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36

    // Build features
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true

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
    }
    // This adds the generated OpenAPI source code to the main source set.
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    // Compose compiler configuration for Kotlin 2.2.x
    composeOptions {
        kotlinCompilerExtensionVersion = ("1.8.2")
    }

    // Kotlin compiler options
    kotlin {
        jvmToolchain(24)
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn"
            )
        }
    }

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Build types
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }

    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    }

    tasks.openApiGenerate {
        generatorName.set("kotlin")
        // Use the correct file URI for the OpenAPI spec
        inputSpec.set("file:///C:/Users/Wehtt/OneDrive/Desktop/ReGenesis-fix-dependabot-compose-plugin/ReGenesis-patch1/app/api/system-api.yml")
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
        implementation(project(":romtools"))  // Temporarily disabled - Hilt/KSP issues
        implementation(project(":secure-comm"))
        implementation(project(":collab-canvas"))  // Temporarily disabled - YukiHookAPI issues
        implementation(project(":colorblendr"))
        implementation(project(":sandbox-ui"))  // Temporarily disabled - Compose compilation issues
        implementation(project(":datavein-oracle-native"))

        // ===== ANDROIDX & COMPOSE =====
        val composeBom = platform(libs.androidx.compose.bom)
        implementation(composeBom)
        androidTestImplementation(composeBom)

        // Core Compose dependencies
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.core.ktx)

        // Debug implementations
        debugImplementation(libs.bundles.compose.debug)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        // ===== LIFECYCLE =====
        implementation(libs.bundles.lifecycle)

        // ===== DATABASE - ROOM =====
        implementation(libs.bundles.room)

        // ===== DATASTORE =====
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)

        // ===== KOTLIN & COROUTINES =====
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.bundles.coroutines)

        // ===== NETWORKING =====
        implementation(libs.bundles.network)

        // ===== FIREBASE =====
        // Import the Firebase BoM (Bill of Materials)
        implementation(platform(libs.firebase.bom))

        // Use the Firebase SDKs with the versions specified in the BoM
        implementation(libs.bundles.firebase)  // Includes all Firebase SDKs from the versions catalog

        // Firebase UI for Firestore (optional)
        implementation(libs.firebaseui.firebase.ui.firestore.ktx)
        implementation(libs.firebase.ui.storage.ktx)
        implementation(libs.firebaseui.firebase.ui.database.ktx)
        implementation(libs.firebaseui.firebase.ui.auth.ktx)
        implementation(libs.firebaseui.firebase.ui.messaging.ktx)
        implementation(libs.firebaseui.firebase.ui.config.ktx)
        implementation(libs.firebase.ui.perf.ktx)
        implementation(libs.firebaseui.firebase.ui.auth.ktx)
        implementation(libs.firebase.ui.storage.ktx)
        implementation(libs.firebaseui.firebase.ui.database.ktx)
        implementation(libs.firebaseui.firebase.ui.auth.ktx)
        implementation(libs.firebaseui.firebase.ui.messaging.ktx)
        implementation(libs.firebaseui.firebase.ui.config.ktx)
        implementation(libs.firebase.ui.perf.ktx)
        implementation(libs.firebaseui.firebase.ui.auth.ktx)


        // ===== HILT DEPENDENCY INJECTION =====
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // ===== UTILITIES =====
        implementation(libs.timber)
        implementation(libs.coil.compose)

        // ===== SECURITY =====
        implementation(libs.androidx.security.crypto)

        // ===== CORE LIBRARY DESUGARING =====
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        // ===== XPOSED/LSPosed Integration =====
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))

        // --- TESTING ---
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing) // For Hilt in Android tests

        // --- DEBUGGING ---
        debugImplementation(libs.leakcanary.android)
        implementation(libs.kotlin.stdlib.jdk8)
        implementation(libs.kotlin.reflect)

    }

