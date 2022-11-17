plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = Config.targetSdk

    defaultConfig {
        applicationId = Config.appId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            // Proguard will work only in release build
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
    }
    packagingOptions {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    // Core
    implementation(Deps.core)
    implementation(Deps.lifecycle)
    implementation(Deps.activityCompose)

    // Compose
    implementation(Deps.composeUI)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeToolingPreview)
    debugImplementation(Deps.composeUITooling)
    debugImplementation(Deps.composeUITestManifest)

    // Testing
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junitExt)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.composeUITestJunit)

    // Ktor
    implementation(Deps.ktorClientCore)
    implementation(Deps.ktorClientCio)
    implementation(Deps.ktorSerialization)
    implementation(Deps.ktorClientContentNegotiation)

    // Coil
    implementation(Deps.coil)

    // Dagger Hilt
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltCompose)
}
