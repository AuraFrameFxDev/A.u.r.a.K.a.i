plugins {
    id("com.android.library") version libs.versions.agp
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.aurakai.auraframefx.romtools"
    compileSdk = 35

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }


        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"

    }
}

dependencies {
    // Module dependencies
    api(project(":core-module"))
    implementation(project(":secure-comm"))

    // Core Android
    implementation(libs.androidx.core.ktx)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)

    // Debug tools
    debugImplementation(libs.leakcanary.android)
}

val romToolsOutputDirectory: DirectoryProperty =
    project.objects.directoryProperty().convention(layout.buildDirectory.dir("rom-tools"))

// Copy task
tasks.register<Copy>("copyRomTools") {
    from("src/main/resources")
    into(romToolsOutputDirectory)
    include("**/*.so", "**/*.bin", "**/*.img", "**/*.jar")
    includeEmptyDirs = false
    doFirst {
        romToolsOutputDirectory.get().asFile.mkdirs()
        logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}")
    }
    doLast {
        logger.lifecycle("✅ ROM tools copied to: ${romToolsOutputDirectory.get().asFile}")
    }
}

// Verification task
tasks.register("verifyRomTools") {
    dependsOn("copyRomTools")
}


tasks.named("build") {
    dependsOn("verifyRomTools")
}

tasks.register("romStatus") {
    group = "aegenesis"; doLast { println("🛠️ ROM TOOLS - Ready (Java 24)") }
}
