plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "me.tbandawa.android.aic.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "me.tbandawa.android.aic.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.data)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.splashscreen)
    implementation(libs.paging.common)
    implementation(libs.paging.compose.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)
    implementation(libs.coil.compose)
    implementation(libs.timber.debug)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    debugImplementation(libs.compose.ui.tooling)
}