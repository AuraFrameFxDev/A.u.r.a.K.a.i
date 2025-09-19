# Universal Genesis Protocol Configuration

## Step 1: Run the Universal Configuration Script
Execute `universal-config.bat` to apply base settings to all modules.

## Step 2: Manual Repository Fix (settings.gradle.kts)
Add these repositories to your `dependencyResolutionManagement` block:

```kotlin
repositories {
    google()
    mavenCentral()
    maven { url = uri("https://api.xposed.info/") }        // YukiHookAPI
    maven { url = uri("https://jitpack.io") }               // LSPosed & GitHub packages
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") } // Sonatype
    maven { url = uri("https://repo.maven.apache.org/maven2/") } // Additional Maven
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") } // JetBrains Compose
}
```

## Step 3: Universal Module Template
Every module should have this base configuration:

```kotlin
// Java 24 / JVM 23 for ALL modules
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

compilerOptions {
    jvmTarget.set(JvmTarget.JVM_23)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}
```

## Step 4: NDK Modules Configuration
For `datavein-oracle-native`, `secure-comm`, and any native modules:

```kotlin
android {
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.31.6"
        }
    }
    ndkVersion = "27.0.12077973"
}
```

## Step 5: Universal Dependencies
Add to all applicable modules:

```kotlin
dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    
    // Testing universally
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}
```

## Step 6: Xposed Modules
For `utilities`, `romtools`, and any Xposed-related modules:

```kotlin
dependencies {
    implementation(libs.yukihook.api)
    implementation(libs.lsposed.api)
    ksp(libs.yukihook.ksp)
}
```

This ensures every module has consistent Java 24, NDK support where needed, proper repositories, and all necessary dependencies.
