import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Core Android Application Conventions
    id("genesis.android.application")
    
    // Feature Conventions
    id("genesis.android.compose")
    id("genesis.android.dagger.hilt")
    
    // Firebase
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    
    // KSP for annotation processing
    alias(libs.plugins.ksp)
    
    // OpenAPI Generator
    id("org.openapi.generator") version "7.15.0"
    
    // Kotlin serialization
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "dev.aurakai.auraframefx"

    compileSdk = 34

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    // Source set for generated OpenAPI
    sourceSets {
        getByName("main") {
            kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
        }
    }

    buildFeatures {
        aidl = true
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        isCoreLibraryDesugaringEnabled = true
    }

    // Configure Java toolchain
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(24)
        }
    }

// Configure Kotlin compilation
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21) // Use enum, not string
            freeCompilerArgs.addAll(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
            )
        }
        dependsOn(tasks.named("openApiGenerate"))
    }

// Configure Java compilation
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_24.toString()
        targetCompatibility = JavaVersion.VERSION_24.toString()
        // options.compilerArgs.add("--enable-preview") // Removed this line
    }

// OpenAPI Generator configuration
    tasks.named<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerate") {
        generatorName.set("kotlin")
        // Use project-relative path for the OpenAPI spec
        inputSpec.set(project.layout.projectDirectory.file("api/system-api.yml").asFile.absolutePath)
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

// Apply the standardized status task plugin
    apply(plugin = "genesis.tasks.status")

// Clean task for generated sources
    tasks.register<Delete>("cleanGeneratedSources") {
        delete(
            layout.buildDirectory.dir("generated"),
            layout.buildDirectory.dir("tmp/kapt3"),
            layout.buildDirectory.dir("tmp/kotlin-classes"),
            layout.buildDirectory.dir("kotlin")
        )
    }

// Ensure clean runs before build
    tasks.named("clean") {
        dependsOn("cleanGeneratedSources")
    }

// Hilt: Allow references to generated code
    // Configure kapt
    plugins.withId("org.jetbrains.kotlin.kapt") {
        configure<org.jetbrains.kotlin.gradle.plugin.KaptExtension> {
            correctErrorTypes = true
        }
    }

    dependencies {
        // Core AndroidX
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)

        // Compose BOM
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        implementation(platform("androidx.compose:compose-bom:2024.02.02"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        // Material 3
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.material:material-icons-extended")

        // Navigation
        implementation(libs.androidx.navigation.compose)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Coroutines
        implementation(libs.bundles.coroutines)

        // Firebase (use direct dependency strings for missing catalog entries)
        implementation(platform(libs.firebase.bom))
        implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-firestore")
        implementation("com.google.firebase:firebase-database")
        implementation("com.google.firebase:firebase-storage")
        implementation("com.google.firebase:firebase-config")
        implementation("com.google.firebase:firebase-messaging")
        implementation("com.google.firebase:firebase-crashlytics")

        // Network
        implementation(libs.bundles.network)
        implementation(libs.kotlinx.serialization.json)

        // Image Loading
        implementation(libs.coil.compose)

        // Logging
        implementation(libs.timber)

        // Desugaring
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        // Testing
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
        androidTestImplementation(platform(libs.androidx.compose.bom))

        // Debug
        debugImplementation(libs.leakcanary.android)

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

        // XPOSED/LSPosed Integration
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))
        implementation("androidx.datastore:datastore-preferences:1.1.7")

        // Coroutines / serialization / datetime
        implementation(libs.kotlinx.serialization.json)
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1-0.6.x-compat")

        // ===== NETWORKING =====
        implementation(libs.bundles.network)

        // ===== FIREBASE =====
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.analytics)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation("com.google.firebase:firebase-database")
        implementation(libs.firebase.storage)
        implementation(libs.firebase.config)
        implementation(libs.firebase.messaging)
        implementation(libs.firebase.crashlytics)

        // FirebaseUI (optional)
        implementation(libs.firebase.auth)
        implementation("com.google.firebase:firebase-database")
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.storage)

        // ===== HILT DEPENDENCY INJECTION =====
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        implementation(libs.hilt.navigation.compose)
        implementation(libs.hilt.work)

        // Images / utils
        implementation(libs.coil.compose)
        implementation(libs.timber)

        // Debug tools
        debugImplementation(libs.leakcanary.android)
        debugImplementation(libs.bundles.compose.debug)
        debugImplementation(libs.androidx.compose.ui.test.manifest)
        implementation(libs.kotlin.stdlib.jdk8)
        implementation(libs.kotlin.reflect)
    }
}
