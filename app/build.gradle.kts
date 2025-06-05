plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "org.hoshiro.littlelemon"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.hoshiro.littlelemon"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //Firebase

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-auth")

    //gmail
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    //implementation("com.google.android.gms:play-services-auth:20.7.0")

    //Google auth
    implementation ("androidx.credentials:credentials:<latest version>")
    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.1")



    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    //implementation ("androidx.core:core-ktx:1.15.0")

    //Navigation
    implementation ("androidx.navigation:navigation-compose:2.8.2")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.8.2")


    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //Ktor
    implementation ("io.ktor:ktor-client-android:2.1.3")
    implementation ("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation ("io.ktor:ktor-serialization-kotlinx-json:2.1.3")

    //Room
    implementation ("androidx.room:room-runtime:2.7.1")
    kapt ("androidx.room:room-compiler:2.7.1")

    //Glide
    //implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Coil
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}