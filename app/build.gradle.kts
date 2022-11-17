plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.amuse.codeassignment"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
    }
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
}
