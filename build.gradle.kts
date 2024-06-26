
buildscript{

    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("kapt") version "1.9.22"
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    // for ktor serialization
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
    id("io.realm.kotlin") version "1.13.0" apply false
    // To run JUnit5
    id("de.mannodermaus.android-junit5") version "1.10.0.0" apply false

}

