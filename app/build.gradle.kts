plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.eclipticongovindtest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eclipticongovindtest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        dataBinding{
            enable = true
        }
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    val nav_version = "2.8.1"

// Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation ("com.hbb20:ccp:2.5.1")

    // Optional - RxJava2 library
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")


//Coroutains"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6") //viewModel scope
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6") //lifecycle scope
    implementation ("androidx.fragment:fragment-ktx:1.8.3")

//Lifecycle
    implementation ("androidx.lifecycle:lifecycle-common:2.8.6")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
//size dp/sp



    implementation("com.google.android.material:material:1.12.0")
//optional to handle Serializable
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    var life_versions = "1.1.1"

// Lifecycle components
    implementation ("android.arch.lifecycle:extensions:$life_versions")
    annotationProcessor ("android.arch.lifecycle:compiler:$life_versions")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    var room_version = "2.6.1"

    /*room*/
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")
}




kapt { generateStubs = true }