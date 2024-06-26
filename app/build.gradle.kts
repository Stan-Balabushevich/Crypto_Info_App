plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // for ktor
    id ("org.jetbrains.kotlin.plugin.serialization")
    id ("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("io.realm.kotlin")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = "id.slava.nt.cryptocurrencyinfoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.slava.nt.cryptocurrencyinfoapp"
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val junitJupiterVersion by extra("5.10.2")

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // serialization-json for Retrofit
    implementation ("com.google.code.gson:gson:2.10.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.10.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-android-compiler:2.50")
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Realm db
    implementation("io.realm.kotlin:library-base:1.11.0")


    // Ktor
    implementation ("io.ktor:ktor-client-core:2.3.8")
    implementation ("io.ktor:ktor-client-android:2.3.8")
    implementation ("io.ktor:ktor-client-serialization:2.3.8")
    implementation ("io.ktor:ktor-client-content-negotiation:2.3.8")
    implementation ("io.ktor:ktor-client-logging:2.3.8")
    implementation ("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    testImplementation ("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation ("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.0")
//    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.12")

//     Android test
//    androidTestImplementation ("io.mockk:mockk-android:1.13.9")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

// Mockito
    testImplementation ("org.mockito:mockito-core:4.9.0")
    androidTestImplementation ("org.mockito:mockito-android:4.0.0")

    // testing flows
    testImplementation ("app.cash.turbine:turbine:0.12.1")
    androidTestImplementation ("app.cash.turbine:turbine:0.12.1")

}


