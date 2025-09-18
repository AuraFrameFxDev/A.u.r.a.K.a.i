// ==== GENESIS PROTOCOL - SANDBOX UI ====
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17) // Specify your desired Java version here
        }
    }
    // Set Kotlin JVM target using tasks.withType
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }



    buildFeatures {
        compose = true
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
        implementation(libs.hilt.android); ksp(libs.hilt.compiler)
        implementation("androidx.compose.ui:ui-tooling-preview:1.9.1")
        debugImplementation(libs.bundles.compose.debug)
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Coroutines
        implementation(libs.bundles.coroutines)
        implementation(libs.timber); implementation(libs.coil.compose)
        // Testing
        // Testing
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
        doLast { println("\uD83D\uDCE6 SANDBOX UI - Ready (Java 17)") }
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
}
