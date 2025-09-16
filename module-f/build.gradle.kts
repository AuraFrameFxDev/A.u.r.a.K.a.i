// GENESIS PROTOCOL - MODULE F
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.module.f"
<<<<<<< Updated upstream
    compileSdk = 36

    defaultConfig {
        minSdk = 34
=======
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
>>>>>>> Stashed changes
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
    }
<<<<<<< Updated upstream

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
        }
    }


    dependencies {
        implementation(project(":core-module"))
        implementation(libs.androidx.core.ktx)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)


        // Add other module-specific dependencies here
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    }

    tasks.register("moduleFStatus") {
        group = "aegenesis"
        doLast { println("📦 MODULE F - Ready (Java 24)") }
    }
=======
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Add other module-specific dependencies here
    implementation(libs.kotlin.stdlib.jdk8)
}

tasks.register("moduleFStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 MODULE F - Ready (Java 24)") }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
