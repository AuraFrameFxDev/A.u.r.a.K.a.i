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
