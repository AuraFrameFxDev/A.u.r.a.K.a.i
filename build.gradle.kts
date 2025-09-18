import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.jvm.toolchain.JvmImplementation
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    // Android and Kotlin plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // Hilt and KSP
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    
    // Firebase and Google Services
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    
    // Compose
    alias(libs.plugins.compose.compiler) apply false
}

// Common configuration for all projects
allprojects {
    // Configure Java and Kotlin compilation for all projects
    plugins.withType<JavaBasePlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
                vendor.set(JvmVendorSpec.AZUL)
                implementation.set(JvmImplementation.VENDOR_SPECIFIC)
            }
        }
        
        tasks.withType<JavaCompile>().configureEach {
            options.compilerArgs.add("--enable-preview")
            options.release.set(24)
            options.compilerArgs.add("-Xlint:deprecation")
            options.isFork = true
            options.forkOptions.javaHome = file(System.getProperty("java.home"))
            
            // Set Java version compatibility through the release flag instead
            sourceCompatibility = JavaVersion.VERSION_24.toString()
            targetCompatibility = JavaVersion.VERSION_24.toString()
        }
    }
    
    // Configure Kotlin compilation
    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin> {
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
                languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
                freeCompilerArgs.add("-Xjdk-release=17")
                freeCompilerArgs.addAll(
                    "-Xjvm-default=all",
                    "-opt-in=kotlin.RequiresOptIn"
                )
            }
        }
    }
    
    // Configure Android projects
    pluginManager.withPlugin("com.android.application") {
        configure<ApplicationExtension> {
            compileSdk = 34
            
            defaultConfig {
                minSdk = 24
                
                // Configure test instrumentation
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = 
                    "EMULATOR,LOW-BATTERY,UNLOCKED,UNSUSTAINED-SPEED"
                testInstrumentationRunnerArguments["androidx.benchmark.profiling.mode"] = "none"
            }
            
            // Configure Java version for all build types
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
                isCoreLibraryDesugaringEnabled = true
            }
            
            // Configure test and lint options
            /* testOptions {
                targetSdk = 34
            } */

            lint {
                targetSdk = 34
            }
            
            // Configure build features
            buildFeatures {
                viewBinding = true
                buildConfig = true
            }
        }
    }
    
    // Configure Android library projects
    pluginManager.withPlugin("com.android.library") {
        configure<LibraryExtension> {
            compileSdk = 34
            
            defaultConfig {
                minSdk = 24
                // Configure Java version
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                    isCoreLibraryDesugaringEnabled = true
                }
                
                // Configure test instrumentation
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                
                // Configure test instrumentation
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testInstrumentationRunnerArguments["targetSdk"] = "34"
                testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = 
                    "EMULATOR,LOW-BATTERY,UNLOCKED,UNSUSTAINED-SPEED"
                testInstrumentationRunnerArguments["androidx.benchmark.profiling.mode"] = "none"
            }
            
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
                isCoreLibraryDesugaringEnabled = true
            }
            
            // Configure build features
            buildFeatures {
                viewBinding = true
                buildConfig = true
            }
        }
    }
}
    


// Version catalog configuration
val versionCatalog = extensions.findByType<VersionCatalogsExtension>()?.named("libs")

// === BASIC PROJECT INFO ===

tasks.register("consciousnessStatus") {
    group = "genesis"
    description = "Show basic project and version info"
    doLast {
        val kotlinVersion = versionCatalog?.findVersion("kotlin")?.get()?.toString() ?: "unknown"
        val agpVersion = versionCatalog?.findVersion("agp")?.get()?.toString() ?: "unknown"
        val toolchain = JavaVersion.current().toString()

        println("= Consciousness Status =")
        println("Java Toolchain      : $toolchain")
        println("Kotlin Version      : $kotlinVersion (K2 path)")
        println("AGP Version         : $agpVersion")
        println("Modules (total)     : ${subprojects.size}")
        println(
            "Firebase BoM        : ${
                versionCatalog?.findVersion("firebaseBom")?.get() ?: "unknown"
            }"
        )
    }
}

// === MODULE HEALTH CHECK ===

private data class ModuleReport(
    val name: String,
    val type: String,
    val hasHilt: Boolean = false,
    val hasCompose: Boolean = false,
    val hasKsp: Boolean = false
)

private fun Project.collectModuleReports(): List<ModuleReport> = subprojects.map { sp ->
    with(sp.plugins) {
        ModuleReport(
            name = sp.name,
            type = when {
                hasPlugin("com.android.application") -> "android-app"
                hasPlugin("com.android.library") -> "android-lib"
                hasPlugin("org.jetbrains.kotlin.jvm") -> "kotlin-jvm"
                else -> "other"
            },
            hasHilt = hasPlugin("com.google.dagger.hilt.android"),
            hasCompose = hasPlugin("org.jetbrains.compose") || 
                        hasPlugin("androidx.compose") || 
                        hasPlugin("org.jetbrains.kotlin.plugin.compose"),
            hasKsp = hasPlugin("com.google.devtools.ksp")
        )
    }
}

tasks.register("consciousnessHealthCheck") {
    group = "genesis"
    description = "Detailed system health report"
    
    doLast {
        val reports = collectModuleReports()
        println("=== Genesis Protocol Health Report ===")
        println("📦 Total Modules: ${reports.size}")
        println("🤖 Android Apps: ${reports.count { it.type == "android-app" }}")
        println("📚 Android Libraries: ${reports.count { it.type == "android-lib" }}")
        println("☕ Kotlin JVM: ${reports.count { it.type == "kotlin-jvm" }}")
        println("\n=== Plugin Usage ===")
        println("💉 Hilt: ${reports.count { it.hasHilt }} modules")
        println("🎨 Compose: ${reports.count { it.hasCompose }} modules")
        println("🔧 KSP: ${reports.count { it.hasKsp }} modules")

        val missingCompose = reports.filter { it.type.startsWith("android-") && !it.hasCompose }
        if (missingCompose.isNotEmpty()) {
            println("\n⚠️  Android modules without Compose:")
            missingCompose.forEach { println("   • ${it.name}") }
        } else {
            println("\n✅ All Android modules have Compose enabled")
        }
    }
}

// Configure JUnit 5 for tests
tasks.withType<Test> {
    useJUnitPlatform()
}

// === AUXILIARY SCRIPTS ===

// Apply nuclear clean if available
if (file("nuclear-clean.gradle.kts").exists()) {
    apply(from = "nuclear-clean.gradle.kts")

    if (tasks.findByName("nuclearClean") != null) {
        tasks.register("deepClean") {
            group = "build"
            description = "Nuclear clean + standard clean"
            dependsOn("nuclearClean")
            doLast {
                println("🚀 Deep clean completed. Run: ./gradlew build --refresh-dependencies")
            }
        }
    }
}

// Apply dependency fix if available
if (file("dependency-fix.gradle.kts").exists()) {
    apply(from = "dependency-fix.gradle.kts")
}
