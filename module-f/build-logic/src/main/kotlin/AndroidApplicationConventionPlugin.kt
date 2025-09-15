// ==== GENESIS PROTOCOL - ANDROID APPLICATION CONVENTION ====
// Main application module configuration

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*

class AndroidApplicationConventionPlugin : Plugin<Project> {
    /**
     * Configures an Android application Project with Genesis conventions.
     *
     * Applies the Android application and Kotlin Android plugins; sets Android defaults
     * (compile/target/min SDKs, test runner, vector drawables), build types (release ProGuard
     * optimization and resource shrinking), enabled build features (Compose, BuildConfig),
     * compile options (core library desugaring), packaging exclusions and JNI packaging rules,
     * and lint settings. Also sets the Java toolchain to Java 24, registers a `cleanKspCache`
     * Delete task that removes common KSP/KAPT generated caches, and makes the `preBuild`
     * task depend on that clean task.
     */
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = 35

                defaultConfig {
                    targetSdk = 35
                    minSdk = 34

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                buildFeatures {
                    compose = true
                    buildConfig = true
                    viewBinding = false
                    dataBinding = false
                }

                compileOptions {
                    // App module DOES get desugaring dependency
                    isCoreLibraryDesugaringEnabled = true
                }

                packaging {
                    resources {
                        excludes += setOf(
                            "/META-INF/{AL2.0,LGPL2.1}",
                            "/META-INF/AL2.0",
                            "/META-INF/LGPL2.1",
                            "/META-INF/DEPENDENCIES",
                            "/META-INF/LICENSE",
                            "/META-INF/LICENSE.txt",
                            "/META-INF/NOTICE",
                            "/META-INF/NOTICE.txt",
                            "META-INF/*.kotlin_module",
                            "**/kotlin/**",
                            "**/*.txt"
                        )
                    }
                    jniLibs {
                        useLegacyPackaging = false
                        pickFirsts += listOf("**/libc++_shared.so", "**/libjsc.so")
                    }
                }

                lint {
                    warningsAsErrors = false
                    abortOnError = false
                    disable.addAll(listOf("InvalidPackage", "OldTargetApi"))
                }
            }

            extensions.configure<JavaPluginExtension>("java") {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
            }

            // Clean tasks for app module
            tasks.register("cleanKspCache", Delete::class.java) {
                group = "build setup"
                description = "Clean KSP caches (fixes NullPointerException)"
                delete(
                    layout.buildDirectory.dir("generated/ksp"),
                    layout.buildDirectory.dir("tmp/kapt3"),
                    layout.buildDirectory.dir("tmp/kotlin-classes"),
                    layout.buildDirectory.dir("kotlin"),
                    layout.buildDirectory.dir("generated/source/ksp")
                )
            }

            tasks.named("preBuild") {
                dependsOn("cleanKspCache")
            }
        }
    }
}
