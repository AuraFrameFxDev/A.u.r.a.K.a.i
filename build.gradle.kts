// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

// Java 25 with auto-provisioning
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        // Gradle 9.1.0 will auto-download Java 25 if needed
    }
}

// Find version catalog
val versionCatalog = extensions
    .findByType<VersionCatalogsExtension>()
    ?.named("libs")

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

// Dependencies for the build-logic module itself

// Dependencies for the build-logic module itself
dependencies {
    // Version catalog access - using the same version catalog as the main project
    implementation(files(project.rootProject.file("gradle/libs.versions.toml")))

    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.1.0")

    // Kotlin Gradle Plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.0")

    // Google Services and Firebase
    implementation("com.google.gms:google-services:4.4.2")

    // Hilt
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.50")

    // KSP
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.0-1.0.21")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("io.mockk:mockk:1.13.5")
}

// Configure Java toolchain for build-logic module
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

        register("statusTask") {
            id = "genesis.tasks.status"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.StatusTaskConventionPlugin"
        }
    }
}

// Configure Kotlin settings for build-logic module
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}

// Enable Gradle's configuration cache
tasks.withType<org.gradle.api.tasks.compile.JavaCompile>().configureEach {
    options.isIncremental = true
}

// Enable parallel execution of tests
tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    testLogging {
        events("passed", "skipped", "failed")
    }
}
