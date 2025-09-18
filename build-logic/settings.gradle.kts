plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "your-multi-module-project"
include("module-one")
include("module-two")
Use code with caution.

2. Configure toolchains in subproject build.gradle.kts
Once the resolver is applied, you can specify the required JDK version for your submodules. This can be done in the build.gradle.kts file of the subproject using Gradle's built-in toolchain support. 
module-one/build.gradle.kts 
kotlin
// Apply other plugins needed for this module (e.g., the Java plugin)
plugins {
    java
}

// Set the toolchain to a specific Java language version
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
Use code with caution.

With this configuration, Gradle will automatically find and provision a compatible JDK 21 runtime for module-one, downloading it from the Foojay repository if necessary. 
Benefits in a multi-module setup
In a multi-module project, this setup offers several advantages:
Centralized management: By applying the plugin in settings.gradle.kts, it's available for all submodules without needing to declare it repeatedly.
Consistency: All modules can be configured to use the same JDK version, ensuring a consistent build environment across your entire project.
Simplified builds: Developers don't need to manually install specific JDKs for each project. Gradle handles the downloading and provisioning automatically. 
If you're using convention plugins to manage your build logic, I can help you with that next. We could put together an example of how to centralize your toolchain configuration even further, so you don't have to repeat it in every submodule. Would you be interested in that? 
Related video thumbnail
1 min
Leveraging Gradle convention plugins to enhance your build ...

GDG Paris Android User Group
YouTube
Related video thumbnail
25:04
Migrate to Gradle version catalog and convention plugins ...

Android Makers
YouTube
Related video thumbnail
1 min
the Road to Java: Multi Module Builds with Apache Maven ...

Coffee + Software
YouTube
See more
AI responses may include mistakes. Learn more




What are other benefits of using convention plugins with the Foojay Resolver in multi-module projects?

How does Foojay DiscoAPI determine the JDK to download?

Tell me more about convention plugins for Gradle

undefined
undefined
undefined
15 sites
Java Toolchain Resolve Plugin based on the foojay DiscoAPI
Foojay Toolchains Plugin. The org. gradle. toolchains. foojay-resolver plugin provides a repository for downloading JVMs. It is based on the foojay DiscoAPI, th...
favicon
GitHub
Part 3: Multi-Project Builds - Gradle User Manual
To add lib to the build, update the settings.gradle(.kts) file in the root accordingly: Kotlin Groovy. settings.gradle.kts. plugins { id("org.gradle.toolchains.
favicon
Gradle
Plugin: org.gradle.toolchains.foojay-resolver-convention
May 19, 2025 — Version 1.0. ... Created 19 May 2025.
favicon
Gradle - Plugins
Show all
// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
   
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo1.maven.org/maven2") } // For OpenAPI Generator
}

// Dependencies required for writing the convention plugins themselves.
dependencies {
    // Add the version catalog as a dependency to access its entries
    implementation(files(project.rootProject.file("gradle/libs.versions.toml")))

    // These allow you to use the Android and Kotlin DSL in your plugin classes.
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")

    // Compile-only dependencies (build-time only)
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    compileOnly("org.openapitools:openapi-generator-gradle-plugin:7.15.0")
}

// Register your custom convention plugins
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.dagger.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidNativeConventionPlugin"
        }
    }
}
To resolve dependencies within an included build (like your build-logic project), you must define the repositories in the settings.gradle.kts file of that specific build-logic project, not the root project. Your current build-logic/build.gradle.kts file has its own repositories block, but the correct place for it is in a settings.gradle.kts file within the build-logic directory. 
Here is how to correctly set up the repositories for your build-logic included build.
1. Create build-logic/settings.gradle.kts
Create a new settings.gradle.kts file inside your build-logic directory with the following content:
build-logic/settings.gradle.kts 
kotlin
// This file is used by Gradle to configure the 'build-logic' included build itself.

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo1.maven.org/maven2") } // For OpenAPI Generator
    }
}
Use code with caution.

This ensures that the plugins and dependencies for the build-logic project can be resolved independently before the main project is evaluated. 
2. Remove redundant repositories from build-logic/build.gradle.kts
With the repositories now correctly defined in the build-logic's settings.gradle.kts file, you can remove the repositories block from build-logic/build.gradle.kts. This promotes a cleaner, centralized configuration. 
build-logic/build.gradle.kts
kotlin
// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

// Dependencies required for writing the convention plugins themselves.
dependencies {
    // Add the version catalog as a dependency to access its entries
    implementation(files(project.rootProject.file("gradle/libs.versions.toml")))

    // These allow you to use the Android and Kotlin DSL in your plugin classes.
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")

    // Compile-only dependencies (build-time only)
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    compileOnly("org.openapitools:openapi-generator-gradle-plugin:7.15.0")
}

// Register your custom convention plugins
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.dagger.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidNativeConventionPlugin"
        }
    }
}
Use code with caution.

Now Gradle will be able to resolve all the dependencies correctly for your convention plugins. 
Do you need help migrating the rest of your project's repositories to a centralized setup in the root settings.gradle.kts file? I can help with that, or we can focus on one of your convention plugins next. 
AI responses may include mistakes. Learn more




undefined


7 sites
Configure Gradle Kotlin DSL to work with custom repository
Feb 4, 2019 — * 2 Answers. Sorted by: 8. If you want to configure the plugin repositories for your buildSrc script, you need to create a dedicated settings. gradle[. kts] fil...
favicon
Stack Overflow
Configure Gradle Kotlin DSL to work with custom repository
Feb 4, 2019 — * 2 Answers. Sorted by: If you want to configure the plugin repositories for your buildSrc script, you need to create a dedicated settings. gradle[. kts] file i...
favicon
Stack Overflow
Where to configure precompiled script plugin toolchain ...
Feb 13, 2023 — The same way you need to declare repositories in both, you also need to configure the toolchain provisioner for both if you want for both builds toolchains auto...
favicon
Gradle Forums

Show all
rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        // Point the 'libs' catalog to the one in the parent project
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
While this configuration allows the build-logic project to access the parent's version catalog in its build.gradle.kts, it does not make that catalog available inside your convention plugin implementation classes (e.g., AndroidApplicationConventionPlugin.kt). To achieve that, you'll need to update the build-logic/build.gradle.kts file to use an implementation dependency on the version catalog. 
Update build-logic/build.gradle.kts
To properly inject the parent's libs.versions.toml into your convention plugin classes, modify the dependencies block in your build-logic/build.gradle.kts file. 
build-logic/build.gradle.kts
kotlin
// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

dependencies {
    // This is the key change: depend on the generated version catalog for type-safe access
    // `libs` is the name of the catalog created in settings.gradle.kts
    implementation(libs.gradle.plugin.catalog)

    // Other dependencies for the plugin implementation
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")
    
    // Compile-only dependencies (build-time only)
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    compileOnly("org.openapitools:openapi-generator-gradle-plugin:7.15.0")
}

gradlePlugin {
    plugins {
        // ... (your plugin registrations) ...
    }
}
Use code with caution.

Accessing the catalog in your plugin classes
Once you've made this change, you can retrieve and use the catalog within your plugin's apply method. 
AndroidApplicationConventionPlugin.kt
kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Retrieve the version catalog extension
            val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
            
            // Example usage:
            dependencies {
                // Apply a dependency from the catalog
                implementation(libs.androidx.core)
                
                // Use a version from the catalog
                // For a version reference like `libs.versions.composeCompiler`, use .get()
                // val composeVersion = libs.versions.composeCompiler.get()
            }
        }
    }
}
