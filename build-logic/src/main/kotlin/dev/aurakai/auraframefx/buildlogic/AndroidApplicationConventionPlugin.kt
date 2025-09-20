package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Standard Android application configuration, now including Hilt and KSP.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    /**
     * Applies a standard Android application convention to the given Gradle project.
     *
     * Configures the project by:
     * - Applying required plugins in the correct order: Android application, Kotlin Android, Hilt, and KSP.
     * - Setting Android ApplicationExtension values (compileSdk 36, minSdk 34, targetSdk 36, test runner, vector drawables).
     * - Defining release and debug build types (release minification + ProGuard; debug suffixes and debuggable).
     * - Reading `java.toolchain` (defaults to "21" when absent) and using it for Java compileOptions and the Kotlin JVM toolchain.
     * - Setting Kotlin JVM bytecode target to JVM_24.
     * - Enabling BuildConfig generation and including Android resources in unit tests.
     * - Excluding AL2.0 and LGPL2.1 license files from packaging resources.
     * - Adding Hilt implementation and KSP compiler dependencies from the version catalog ("libs").
     *
     * Side effects: applies plugins, mutates the Android extension and dependencies of the target project.
     */
    override fun apply(target: Project) {
        with(target) {
            // Define libs accessor
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // Apply the essential plugins IN THE CORRECT ORDER
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android") // RE-ADDED AND ORDERED
            pluginManager.apply("com.google.dagger.hilt.android") // ORDERED
            pluginManager.apply("com.google.devtools.ksp")       // ORDERED

            extensions.configure<ApplicationExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 34
                    targetSdk = 36
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                    debug {
                        isDebuggable = true
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-DEBUG"
                    }
                }

                val toolchainVersion = providers.gradleProperty("java.toolchain").orElse("21").get().toInt()
                val javaCompatibilityVersion = JavaVersion.toVersion(toolchainVersion)
                
                compileOptions {
                    sourceCompatibility = javaCompatibilityVersion
                    targetCompatibility = javaCompatibilityVersion
                }

                // Configure Kotlin JVM toolchain and target
                extensions.getByType(org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension::class.java).apply {
                    jvmToolchain(toolchainVersion) // This sets the JDK for Kotlin compilation
                    compilerOptions {
                        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24) // Explicitly set bytecode target
                    }
                }

                buildFeatures {
                    buildConfig = true
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            // Configure Hilt dependencies
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
                // Add other common Hilt dependencies if all applications using this convention need them:
                // add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                // add("implementation", libs.findLibrary("hilt.work").get())
            }
        }
    }
}
