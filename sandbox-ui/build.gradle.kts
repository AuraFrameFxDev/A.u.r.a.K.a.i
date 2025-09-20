// ==== GENESIS PROTOCOL - SANDBOX UI ====
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.aurakai.auraframefx.sandboxui"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25 // Updated
        targetCompatibility = JavaVersion.VERSION_25 // Updated
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }
    dependencies {
        api(project(":core-module"))
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.compose.material.icons.extended)
        implementation(libs.androidx.compose.ui.tooling.preview)
        debugImplementation(libs.androidx.compose.ui.tooling)
        implementation(libs.hilt.android); ksp(libs.hilt.compiler) // Combined for brevity
        implementation("androidx.compose.ui:ui-tooling-preview:1.9.1") // Consider aliasing or BOM
        debugImplementation(libs.bundles.compose.debug)
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)

        // Hilt (already declared above, this is a duplicate)
        // implementation(libs.hilt.android)
        // ksp(libs.hilt.compiler)

        // Coroutines
        implementation(libs.bundles.coroutines)
        implementation(libs.timber); implementation(libs.coil.compose) // Combined for brevity

        // Testing
        testImplementation(libs.junit4)
        testImplementation(libs.mockk)
        testImplementation(kotlin("test"))

        // Android Testing
        androidTestImplementation(libs.mockk.android)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)

        // Hilt Testing
        testImplementation(libs.hilt.android.testing)
        kspTest(libs.hilt.compiler)
        androidTestImplementation(libs.hilt.android.testing)
        kspAndroidTest(libs.hilt.compiler)
        implementation(libs.kotlin.stdlib.jdk8)
    }

    tasks.register("sandboxStatus") {
        group = "aegenesis"
        doLast { println("\uD83D\uDCE6 SANDBOX UI - Ready (Java 25, JVM 25)") } // Updated
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
}
// Removed duplicate dependencies block below
