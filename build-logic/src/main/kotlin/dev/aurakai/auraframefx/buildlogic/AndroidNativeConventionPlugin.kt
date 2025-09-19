package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Native code configuration for modules with JNI/NDK support
 */
class AndroidNativeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            
            extensions.configure<LibraryExtension> {
                compileSdk = 36
                
                defaultConfig {
                    minSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    
                    // Enable prefab for native dependencies
                    externalNativeBuild {
                        cmake {
                            cppFlags("-std=c++17")
                            arguments("-DANDROID_STL=c++_shared")
                        }
                    }
                }
                
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_25
                    targetCompatibility = JavaVersion.VERSION_25
                }
                
                // Configure native build
                externalNativeBuild {
                    cmake {
                        path = file("src/main/cpp/CMakeLists.txt")
                        version = "3.22.1"
                    }
                }
                
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                
                // Enable prefab for native dependencies
                buildFeatures {
                    prefab = true
                }
            }
        }
    }
}
