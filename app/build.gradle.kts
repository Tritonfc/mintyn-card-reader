plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.starter.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.starter.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keys/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            storeFile = file("../keys/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {

    // AndroidX
    implementation(libs.androidX.coreKtx)
    implementation(libs.androidX.activity)
    implementation(libs.androidX.appCompat)
    implementation(libs.androidX.constraintLayout)
    implementation(libs.androidX.lifecycle.viewModel)
    implementation(libs.androidX.lifecycle.runtime)
    implementation(libs.androidX.lifecycle.livedata)
    implementation(libs.material)

    // Coroutine
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    //
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)


    //Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)

    //Hilt
implementation(libs.dagger.hiltAndroid)
    testImplementation("junit:junit:4.12")
    kapt(libs.dagger.hiltCompiler)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.toolingPreview)

    //Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.android)
    testImplementation(libs.arch.core)
    testImplementation(libs.coroutines.test)

    //Gson
    implementation(libs.gson)




    implementation(libs.nitrozenAndroid)

}