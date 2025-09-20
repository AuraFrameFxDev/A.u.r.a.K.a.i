// build-logic/src/main/kotlin/genesis.android.compose.kts

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    "implementation"(composeBom)
    "androidTestImplementation"(composeBom)

    // Core Compose dependencies
    "implementation"(libs.bundles.compose.ui)
    "debugImplementation"(libs.bundles.compose.debug)
}
