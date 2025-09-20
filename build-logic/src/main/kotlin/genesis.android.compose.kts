// build-logic/src/main/kotlin/genesis.android.compose.kts

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    id("androidx.compose.compiler") // Ensure Compose Compiler plugin is applied
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
    "implementation"(libs.bundles.compose.ui)
    "debugImplementation"(libs.bundles.compose.debug)
}
