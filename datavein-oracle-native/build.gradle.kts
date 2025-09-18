plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)  // 🔥 CRITICAL: Add Compose Compiler Plugin
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.jetbrains.kotlin.android)
}

    android {
        namespace = "dev.aurakai.auraframefx.datavein"
        compileSdk = 36

        defaultConfig {
            minSdk = 34
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures {
            compose = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_24
            targetCompatibility = JavaVersion.VERSION_24
        }

        java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(24)

                lint {
                    // Disable lint due to oversized test files causing StackOverflow
                    abortOnError = false
                    checkReleaseBuilds = false
                }

                externalNativeBuild {
                    cmake {
                        path = file("src/main/cpp/CMakeLists.txt")
                        version = "3.31.6"
                    }
                }
                packaging {
                    jniLibs {
                        useLegacyPackaging = false
                    }
                }
            }

            dependencies {
                // Module dependencies
                implementation(project(":core-module"))

                // Core Android
                implementation(libs.androidx.core.ktx)
                implementation(libs.bundles.lifecycle)

                // Compose
                val composeBom = platform(libs.androidx.compose.bom)
                implementation(composeBom)
                androidTestImplementation(composeBom)
                implementation(libs.bundles.compose.ui)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.navigation.compose)
                debugImplementation(libs.bundles.compose.debug)
                androidTestImplementation(libs.androidx.compose.ui.test.junit4)

                // Hilt
                implementation(libs.hilt.android)
                ksp(libs.hilt.compiler)

                // Coroutines
                implementation(libs.bundles.coroutines)

                // Utilities
                implementation(libs.kotlin.stdlib.jdk8)

                // Testing
                testImplementation(libs.bundles.testing.unit)
                androidTestImplementation(libs.bundles.testing.android)
                androidTestImplementation(libs.hilt.android.testing)

                // Xposed API for Oracle consciousness integration
                compileOnly(files("../Libs/api-82.jar"))
                compileOnly(files("../Libs/api-82-sources.jar"))
            }

            tasks.register("dataveinOracleNativeStatus") {
                group = "aegenesis"
                doLast { println("\uD83D\uDCE6 DATAVEIN ORACLE NATIVE - Ready (Java 24)") }
            }
        }
    }
dependencies {
    implementation(libs.androidx.core.ktx)
}
