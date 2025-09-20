plugins {
    id("genesis.android.application")
    id("genesis.android.compose")
    id("genesis.android.hilt")
    alias(libs.plugins.google.services) // Firebase can stay as a direct alias
    id("org.openapi.generator") version "7.15.0"
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.aurakai.auraframefx"

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        targetSdk = 36
        multiDexEnabled = true
    }

    buildFeatures {
        // compose is handled by genesis.android.compose
        dataBinding = true
        viewBinding = true
        aidl = true
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
            assets.srcDirs("src/main/assets", "xposed")
        }
    }
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
}

dependencies {
    // ===== MODULE DEPENDENCIES =====
    implementation(project(":core-module"))
    implementation(project(":feature-module"))
    implementation(project(":oracle-drive-integration"))
    implementation(project(":romtools"))
    implementation(project(":secure-comm"))

    // From user's example
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}
