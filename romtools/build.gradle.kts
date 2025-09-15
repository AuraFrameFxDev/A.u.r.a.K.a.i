plugins {
    id("com.android.library") version libs.versions.agp
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.aurakai.auraframefx.romtools"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

val romToolsOutputDirectory: DirectoryProperty =
    project.objects.directoryProperty().convention(layout.buildDirectory.dir("rom-tools"))

dependencies {
    api(project(":core-module"))
    implementation(project(":secure-comm"))
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    // Room compiler temporarily disabled
    // ksp(libs.androidx.room.compiler)
    // Replace with individual Firebase dependencies

    implementation(libs.firebase.performance)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(platform(libs.firebase.bom))
    implementation(libs.timber)
    implementation(libs.coil.compose)
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.hilt.android.testing); kspAndroidTest(libs.hilt.compiler)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.hilt.navigation.compose)
}

// Copy task
tasks.register<Copy>("copyRomTools") {
    from("src/main/resources")
    into(romToolsOutputDirectory)
    include("**/*.so", "**/*.bin", "**/*.img", "**/*.jar")
    includeEmptyDirs = false
    doFirst { romToolsOutputDirectory.get().asFile.mkdirs(); logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}") }
    doLast { logger.lifecycle("✅ ROM tools copied to: ${romToolsOutputDirectory.get().asFile}") }
}

// Verification task
tasks.register("verifyRomTools") {
    dependsOn("copyRomTools")
}

tasks.named("build") { dependsOn("verifyRomTools") }

tasks.register("romStatus") {
    group = "aegenesis"; doLast { println("🛠️ ROM TOOLS - Ready (Java 24)") }
}
