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
     * Applies the Android library plugin to the given project and configures common Android library settings.
     *
     * Configures the `android` LibraryExtension with:
     * - compileSdk = 34
     * - defaultConfig: minSdk = 24, testInstrumentationRunner, consumer proguard file
     * - release buildType with ProGuard rules and minification disabled
     * - Java compile options targeting Java 24
     * - Kotlin options: JVM toolchain 24, jvmTarget = JVM_24, and `-opt-in=kotlin.RequiresOptIn`
     * - Enables Compose and sets the Compose Kotlin compiler extension version from the `libs` version catalog
     * - Packaging resources exclusion for `/META-INF/{AL2.0,LGPL2.1}`
     *
     * Side effects: applies the "com.android.library" plugin and mutates the project's Android and Kotlin extension configurations.
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

            composeOptions {
                kotlinCompilerExtensionVersion = project.libs.findVersion("composeCompiler").get().toString()
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}