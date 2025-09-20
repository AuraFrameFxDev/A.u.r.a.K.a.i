plugins {
    id("com.android.application") // TODO: Replace this with your AndroidApplicationConventionPlugin alias
    // Compose plugins
    alias(libs.plugins.compose.compiler)

    // Google services
    alias(libs.plugins.google.services)

    // External plugins
    id("org.openapi.generator") version "7.15.0"

    // Kotlin serialization
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36 // Changed back to Int

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        targetSdk = 36 // Changed back to Int
        multiDexEnabled = true
        // testInstrumentationRunner should be handled by convention plugin
        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        // These should ideally be set by convention plugins if they are common
        // across all application/library modules with compose, databinding etc.
        // For now, keeping them here for clarity in the :app module.
        compose = true
        dataBinding = true
        viewBinding = true
        aidl = true
        // buildConfig should be handled by convention plugin
    }


    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21 // Updated
            targetCompatibility = JavaVersion.VERSION_21 // Updated
        }

        // Source set for generated OpenAPI and Xposed assets
        sourceSets {
            getByName("main") {
                kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
                assets.srcDirs("src/main/assets", "xposed")  // MODIFIED to include xposed
            }
        }
    }

    // Kotlin JVM target is now set by AndroidApplicationConventionPlugin
    // tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    //     compilerOptions {
    //         jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
    //     }
    // }
}
    tasks.named<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerate") {
        generatorName.set("kotlin")
        inputSpec.set(file("${'$'}{project.projectDir}/api/system-api.yml").path)
        outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.absolutePath)
        apiPackage.set("dev.aurakai.auraframefx.openapi.api")
        modelPackage.set("dev.aurakai.auraframefx.openapi.model")
        invokerPackage.set("dev.aurakai.auraframefx.openapi.invoker")
        configOptions.set(
            mapOf(
                "dateLibrary" to "java8",
                "library" to "jvm-ktor"
            )
        )
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn(tasks.named("openApiGenerate"))
        // The following might be redundant if the convention plugin handles Kotlin options comprehensively
        // compilerOptions {
        //    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        // }
    }

    dependencies {
        // ===== MODULE DEPENDENCIES =====
        implementation(project(":core-module"))
        implementation(project(":feature-module"))
        implementation(project(":oracle-drive-integration"))
        implementation(project(":romtools"))
        implementation(
            project(":secure-comm")
        )
    }
