plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
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
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(24)
        }

        buildFeatures {
            compose = true
            buildConfig = true


            val romToolsOutputDirectory: DirectoryProperty =
                project.objects.directoryProperty()
                    .convention(layout.buildDirectory.dir("rom-tools"))
        }
        dependencies {
            implementation(libs.androidx.core.ktx)
            // Compose
            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.navigation.compose)
            debugImplementation(libs.androidx.compose.ui.tooling)
            debugImplementation(libs.androidx.compose.ui.test.manifest)

            // AndroidX core
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

            // FirebaseUI (optional) - assuming these are what you mean by firebase.auth, etc again
            implementation(libs.firebase.auth)
            implementation(libs.firebase.database)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.storage)

            // ===== HILT DEPENDENCY INJECTION =====
            implementation(libs.hilt.android)
            ksp(libs.hilt.compiler)
            implementation(libs.hilt.work)

            // Images / utils
            implementation(libs.coil.compose)
            implementation(libs.timber)

            // Debug tools
            debugImplementation(libs.leakcanary.android)
            debugImplementation(libs.bundles.compose.debug)
            debugImplementation(libs.androidx.compose.ui.test.manifest)

            // YukiHook / LSPosed
            implementation(libs.yukihook.api)
            ksp(libs.yukihook.ksp)
            implementation(libs.lsposed.api)

            // Testing
            testImplementation(libs.junit4)
            testImplementation(libs.mockk)
            testImplementation(kotlin("test"))

            // Android Testing
            androidTestImplementation(libs.mockk.android)

            androidTestImplementation(platform(libs.androidx.compose.bom))
            androidTestImplementation(libs.androidx.compose.ui.test.junit4)

            // Hilt
            implementation(libs.hilt.android)
            ksp(libs.hilt.compiler)
            implementation(libs.hilt.work)


            // Testing
            testImplementation(libs.mockk.android)
            androidTestImplementation(libs.mockk.android)


            // Copy task
            val romToolsOutputDirectory: Provider<Directory> = layout.buildDirectory.dir("intermediates/romtools")

// Now, within your tasks.register<Copy>("copyRomTools") block:
            tasks.register<Copy>("copyRomTools") {
                from("src/main/resources")
                into(romToolsOutputDirectory) // Correctly uses the Provider<Directory>
                include("**/*.so", "**/*.bin", "**/*.img", "**/*.jar")
                includeEmptyDirs = false
                doFirst {
                    // Access the actual File object safely when the task executes
                    romToolsOutputDirectory.get().asFile.mkdirs()
                    logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}")
                }
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
        }
    }
}

