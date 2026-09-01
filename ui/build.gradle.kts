plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android { namespace = "com.example.giveu.ui"; compileSdk = 35
    defaultConfig { minSdk = 24 }
    buildFeatures { viewBinding = true }
    sourceSets["main"].res.srcDirs("../app/src/main/res")
    compileOptions { sourceCompatibility = JavaVersion.VERSION_11; targetCompatibility = JavaVersion.VERSION_11 }
    kotlinOptions { jvmTarget = "11" }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.cardview:cardview:1.0.0")
    implementation(libs.androidx.activity)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.androidx.work.runtime)
}
