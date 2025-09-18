plugins {
    id("com.android.application") version "9.0.0-alpha02"
    id("org.jetbrains.kotlin.android") version "2.2.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    id("com.google.devtools.ksp") version "2.2.20-2.0.3"
    id("com.google.dagger.hilt.android") version "2.57.1"
}

android {
    namespace = "dev.aurakai.auraframefx.romtools"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_23
    }

    kotlin {
        jvmToolchain(24)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
    }
}

val romToolsOutputDirectory: DirectoryProperty =
    project.objects.directoryProperty().convention(layout.buildDirectory.dir("rom-tools"))

dependencies {
    api(project(":core-module"))
    implementation(project(":secure-comm"))
    implementation("androidx.core:core-ktx:1.17.0")
    implementation(platform("androidx.compose:compose-bom:2025.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-compiler:2.57.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.squareup.retrofit2:retrofit:2.12.0")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("androidx.room:room-runtime:2.8.0")
    implementation("androidx.room:room-ktx:2.8.0")
    // Room compiler temporarily disabled
    // ksp("androidx.room:room-compiler:2.8.0")
    implementation("com.google.firebase:firebase-perf")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("io.coil-kt:coil-compose:2.7.0")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")
    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("io.mockk:mockk-android:1.14.5")
    androidTestImplementation("io.mockk:mockk-android:1.14.5")
    testImplementation("com.google.dagger:hilt-android-testing:2.57.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.57.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.09.00"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    implementation("dev.rikka.yukihookapi:yukihookapi:1.1.23")
    implementation("org.lsposed.api:api:1.0.0")
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

tasks.register("romToolsStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 ROMTOOLS - Ready (Java 24)") }
}
