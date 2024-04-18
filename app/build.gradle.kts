import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltPlugin)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.canerture.canerativeaichat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.canerture.canerativeaichat"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties().apply {
            val propsFile = rootProject.file("local.properties")
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            }
        }

        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            localProperties["gemini_api_key"]?.toString() ?: ""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material3.icons.extended)
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.generativeai)

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
    implementation("androidx.compose.ui:ui:1.3.0-alpha02")
    implementation("androidx.compose.foundation:foundation:1.3.0-alpha02")
    implementation("androidx.compose.material:material:1.3.0-alpha02")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling:1.3.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha02")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha01")
    implementation ("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.android.gms:play-services-auth:19.2.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.google.android.material:material:1.5.0")
}