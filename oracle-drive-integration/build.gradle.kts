
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }
    dependencies {
        implementation(project(":core-module"))
        implementation(project(":secure-comm"))

        // Core Android
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        implementation(libs.hilt.work)
        implementation(libs.bundles.coroutines)
        // Add other module-specific dependencies here
        implementation(libs.kotlin.stdlib.jdk8)

    }

    tasks.register("oracleDriveIntegrationStatus") {
        group = "aegenesis"
        doLast { println("☁️ ORACLE DRIVE INTEGRATION - Ready (Java 25, JVM 25)") } // Updated
    }
}
