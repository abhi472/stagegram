plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.abhi.stageapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abhi.stageapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.bundles.lifecycle)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.ui.impl)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.spinner)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firestore)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.workmanager)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)

    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.coil) {
        because("An image loading library for Android backed by Kotlin Coroutines")
    }
    implementation(libs.retrofit)
    implementation(libs.rxjava)
    implementation(libs.gson)
    implementation(libs.reflect)

    implementation(libs.composeFoundation)

    testImplementation(libs.coroutines.test)

    // For making Assertions in Test cases
    testImplementation(libs.google.truth)
    testImplementation(libs.androidCoreTesting)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.androidCoreTesting)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.work.test)

    testImplementation(libs.junit)
    testImplementation(libs.mockitoInline)
    testImplementation(libs.androidxCoreTesting)
    testImplementation(libs.coroutineTest)
    testImplementation(libs.mockito)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.bundles.compose.ui.debug)

}

// Pass options to Room ksp processor
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.generateKotlin", "true")
}