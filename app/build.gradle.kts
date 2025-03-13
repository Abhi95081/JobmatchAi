plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.jobmatchai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jobmatchai"
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.webkit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    implementation ("com.tom-roush:pdfbox-android:2.0.27.0")

    val nav_version = "2.7.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Constraints Layout dependencies.
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Coil for image store and picker from the gallery.
    implementation("io.coil-kt:coil-compose:2.2.2")

    // viewmodel dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    //For ViewModel with LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //runtime live data
    implementation("androidx.compose.runtime:runtime-livedata:1.5.9")

    //password eye
    implementation("androidx.compose.material:material-icons-extended:1.5.4") // Use latest version

        //google drive
//    implementation ("com.google.android.gms:play-services-auth:20.7.0")
//    implementation ("com.google.api-client:google-api-client-android:1.32.1")
//    implementation ("com.google.apis:google-api-services-drive:v3-rev197-1.25.0")
//    implementation ("com.google.auth:google-auth-library-oauth2-http:1.16.0")
}