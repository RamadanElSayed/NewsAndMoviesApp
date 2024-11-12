plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.newsapp"
    compileSdk = 34

    buildFeatures {
        buildConfig = true // Enable BuildConfig for custom fields
    }

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "NEWS_API_KEY", "\"${project.findProperty("NEWS_API_KEY") ?: ""}\"")
        buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL") ?: ""}\"")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3.material3)
    //noinspection UseTomlInstead
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    implementation (libs.androidx.material.icons.extended) // Check for latest version if needed

    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Dagger - Hilt
    implementation(libs.hilt.android)
    ksp (libs.hilt.androidx.compiler)
    implementation( libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation (libs.androidx.material3)

    // Coroutines
    implementation(libs.coroutines.core)
    // Coil
    implementation(libs.coil.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp.logging.interceptor)
    implementation (libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    // Room components
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    implementation(libs.kotlinx.serialization.json)
    // Room with Kotlin extensions
    implementation (libs.androidx.room.ktx)
    //Desuger
    coreLibraryDesugaring(libs.desugar)
    implementation (libs.foundation)
    implementation (libs.pager.indicators)


}
