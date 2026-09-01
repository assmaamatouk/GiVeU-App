plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android { namespace = "com.example.giveu.data"; compileSdk = 35
    defaultConfig { minSdk = 24 }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_11; targetCompatibility = JavaVersion.VERSION_11 }
    kotlinOptions { jvmTarget = "11" }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.androidx.work.runtime)
}
