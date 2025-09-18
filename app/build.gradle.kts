plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // External plugins
    id("org.openapi.generator") version "7.15.0"

    // Kotlin plugins
    kotlin("plugin.serialization") version "2.2.20"
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36


    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        targetSdk = 36
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        aidl = true
    }

    // Java 24 + JVM target 23
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }


    // Source set for generated OpenAPI
    sourceSets {
        getByName("main") {
            kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
        }
    }
}

// At the root of the script (outside android { })
kotlin {
    jvmToolchain(24)
}

tasks.named<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerate") {
    generatorName.set("kotlin")
    inputSpec.set(file("${'$'}{project.projectDir}/api/system-api.yml").path)
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.named("openApiGenerate"))

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
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // AndroidX core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.security.crypto)

    // Lifecycle / Room / Work
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Coroutines / serialization / datetime
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // ===== NETWORKING =====
    implementation(libs.bundles.network)

    // ===== FIREBASE =====
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.config)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.ai)

    // FirebaseUI (optional)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
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
    coreLibraryDesugaring(libs.desugar.jdk.libs)

}