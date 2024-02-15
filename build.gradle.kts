
buildscript{

    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("kapt") version "1.9.22"
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    // for ktor serialization
//    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false

}

