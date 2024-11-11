buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.3")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}