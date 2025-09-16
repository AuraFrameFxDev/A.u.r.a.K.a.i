import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

class GenesisAndroidLibraryPlugin : Plugin<Project> {
    /**
     * Applies the Android library plugin and configures common Android library settings for the project.
     *
     * Configured values:
     *  - Applies the "com.android.library" plugin.
     *  - compileSdk = 34, minSdk = 24.
     *  - testInstrumentationRunner set to "androidx.test.runner.AndroidJUnitRunner".
     *  - Consumer ProGuard rules and release proguard files (minification disabled).
     *  - Java source/target compatibility and Kotlin jvm toolchain set to Java 24 / JvmTarget.JVM_24.
     *  - Kotlin compiler free args includes `-opt-in=kotlin.RequiresOptIn`.
     *  - Enables Compose (buildFeatures.compose = true).
     *  - Excludes "/META-INF/{AL2.0,LGPL2.1}" from packaged resources.
     */
    override fun apply(project: Project) {
        project.plugins.apply("com.android.library")

        // Apply common Android library configuration here
        project.extensions.configure<LibraryExtension>("android") {
            compileSdk = 34

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
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
            }

            // Configure Kotlin options
            project.extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(24)
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlin.RequiresOptIn"
                    )
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