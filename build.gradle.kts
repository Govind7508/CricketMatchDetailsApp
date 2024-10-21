// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.2")
    }
    repositories {
        google()
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

}