// ==== GENESIS PROTOCOL - COLORBLENDR MODULE ====
// Color utility and theming module
plugins {
    alias(libs.plugins.dev.aurakai.auraframefx.android.library) // Using new convention plugin
}

android {
    namespace = "dev.aurakai.auraframefx.colorblendr" // Module-specific

    defaultConfig {
        minSdk = 34 // Specific override for this module (convention plugin default is 24)
    }

    buildFeatures {
        // buildConfig is true for this module.
        // compose = true is handled by the convention plugin.
        buildConfig = true
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.lifecycle)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test) // MODIFIED

    // Android Testing
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")

    // Hilt Testing
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Utilities
    implementation(libs.timber)
    implementation(libs.kotlin.stdlib.jdk8)
}

// Move the tasks registration block outside the dependencies block
tasks.register("colorblendrStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24, JVM 24)") } // UPDATED
}
