import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.*

// Kotlin Android plugin is now included with AGP 9.0+

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Convention plugin for Android Library modules, now also applying Hilt and KSP.
 */
class GenesisAndroidLibraryPlugin : Plugin<Project> {
    /**
     * Applies the Android library plugin and configures common Android library settings for the project.
     */
    override fun apply(project: Project) {
        // Apply the essential plugins IN THE CORRECT ORDER
        project.plugins.apply("com.android.library")
        project.plugins.apply("org.jetbrains.kotlin.android") // RE-ADDED AND ORDERED
        project.plugins.apply("com.google.dagger.hilt.android") // ORDERED
        project.plugins.apply("com.google.devtools.ksp")       // ORDERED

        // Configure Hilt dependencies (using existing libs accessor)
        project.dependencies {
            add("implementation", project.libs.findLibrary("hilt.android").get())
            add("ksp", project.libs.findLibrary("hilt.compiler").get())
        }

        // Apply common Android library configuration here
        project.extensions.configure<LibraryExtension>("android") {
            compileSdk = 36 

            defaultConfig {
                minSdk = 24
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_25
                targetCompatibility = JavaVersion.VERSION_25
            }
            // Configure Kotlin options
            (this as org.gradle.api.plugins.ExtensionAware).extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension> {
                jvmToolchain(24) // This sets the JDK for Kotlin compilation
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24) // Explicitly set bytecode target
                }
                
                sourceSets.all {
                    languageSettings {
                        optIn("kotlin.RequiresOptIn")
                    }
                }
            }

            // Configure Compose
            buildFeatures {
                compose = true
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}