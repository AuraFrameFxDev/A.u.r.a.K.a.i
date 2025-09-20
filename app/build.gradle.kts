plugins {
    alias(libs.plugins.dev.aurakai.auraframefx.android.application) // Using new convention plugin
    alias(libs.plugins.google.services)
    id("org.openapi.generator") version "7.15.0"
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.aurakai.auraframefx"

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx" // App-specific
        multiDexEnabled = true                   // App-specific
    }

    buildFeatures {
        // App-specific features.
        // buildConfig and compose are handled by the convention plugin.
        dataBinding = true
        viewBinding = true
        aidl = true
    }

    // Source set for generated OpenAPI and Xposed assets (App-specific)
    sourceSets {
        getByName("main") {
            kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
            assets.srcDirs("src/main/assets", "xposed")
        }
    }
}

    tasks.named<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerate") {
        generatorName.set("kotlin")
        inputSpec.set(file("${project.projectDir}/api/system-api.yml").path)
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
        // The following is now definitely redundant as the convention plugin handles Kotlin options comprehensively
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
    }
