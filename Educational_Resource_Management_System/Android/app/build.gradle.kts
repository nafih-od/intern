plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.educare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.educare"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.ai.client.generativeai:generativeai:0.1.1") // Ensure this is the correct version

    implementation(files("libs\\picasso-2.5.2.jar"))
    // Other dependencies
//    implementation ("ai.picovoice:porcupine-android:1.9.5")

    implementation ("ai.picovoice:porcupine-android:3.0.0") // Replace with the latest version
    implementation ("com.google.guava:guava:31.0.1-android") // Ensure the version is correct
//    implementation (libs.android.pdf.viewer.v330)
    implementation ("androidx.viewpager2:viewpager2:1.1.0")

}