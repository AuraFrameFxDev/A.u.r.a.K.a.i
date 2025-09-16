plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // Use alias for ksp, ensure version in toml
    alias(libs.plugins.composeCompiler) // Jetpack Compose compiler
    alias(libs.plugins.hilt) // <-- Add this line to apply the Hilt Gradle plugin
    id("com.google.gms.google-services") // Okay to keep this as id
    id("org.openapi.generator") version "7.15.0"
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36

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

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24)
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
            freeCompilerArgs.addAll(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
        }
    }
}

tasks.named("openApiGenerate") {
    configure<org.openapitools.generator.gradle.plugin.tasks.GenerateTask> {
        generatorName.set("kotlin")
        // Use the correct file URI for the OpenAPI spec
        inputSpec.set("file://${project.projectDir}/api/system-api.yml")
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

    // ===== ANDROIDX & COMPOSE =====
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.appcompat) // Explicitly add AppCompat
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // ===== KOTLIN & COROUTINES =====
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // ===== NETWORKING =====
    implementation(libs.bundles.network)
    implementation(libs.squareup.moshi)
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")

    // ===== FIREBASE =====
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // Add the dependencies for Firebase products you want to use
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.ai)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    // ===== WORKMANAGER & HILT WORKER =====
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.work)

    // ===== HILT DEPENDENCY INJECTION =====
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ===== UTILITIES =====
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // ===== SECURITY =====
    implementation(libs.androidx.security.crypto)

    // ===== JACKSON YAML (for OpenAPI Generator compatibility) =====
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.0")

    // ===== CORE LIBRARY DESUGARING =====
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // ===== XPOSED/LSPosed Integration =====
    // compileOnly(files("../Libs/api-82.jar"))
    // compileOnly(files("../Libs/api-82-sources.jar"))

    // --- TESTING ---
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)

    // --- DEBUGGING ---
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.bundles.compose.debug)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.reflect)
}

configurations.all {
    resolutionStrategy {
        force("androidx.appcompat:appcompat:1.7.1")
    }
}
