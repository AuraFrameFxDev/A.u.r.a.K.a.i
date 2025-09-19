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
     *
     * Configured values:
     *  - Applies "com.android.library", "com.google.dagger.hilt.android", and "com.google.devtools.ksp" plugins.
     *  - compileSdk = 36, minSdk = 24.
     *  - testInstrumentationRunner set to "androidx.test.runner.AndroidJUnitRunner".
     *  - Consumer ProGuard rules and release proguard files (minification disabled).
     *  - Java source/target compatibility and Kotlin jvm toolchain set to Java 24 / JvmTarget.JVM_25.
     *  - Kotlin compiler free args includes `-opt-in=kotlin.RequiresOptIn`.
     *  - Enables Compose (buildFeatures.compose = true).
     *  - Excludes "/META-INF/{AL2.0,LGPL2.1}" from packaged resources.
     */
    override fun apply(project: Project) {
        project.plugins.apply("com.android.library")
        project.plugins.apply("com.google.dagger.hilt.android") // ADDED Hilt
        project.plugins.apply("com.google.devtools.ksp")       // ADDED KSP
        // project.plugins.apply("org.jetbrains.kotlin.android") // REMOVED as per AGP 9.0+ guidance

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
                jvmToolchain(24)
                
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