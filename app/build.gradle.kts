plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}
android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "commanderpepper.catdogtwo"
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    //Web related services
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.8.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //Image Loading Glide
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")

    //UI
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    //Room
    implementation("androidx.room:room-runtime:2.2.4")
    implementation("androidx.room:room-ktx:2.2.4")
    annotationProcessor("androidx.room:room-compiler:2.2.4")
    kapt("androidx.room:room-compiler:2.2.4")

    //Material Design
    implementation("com.google.android.material:material:1.1.0")
}
