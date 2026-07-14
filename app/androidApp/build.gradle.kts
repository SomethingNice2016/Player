import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
}

android {
    namespace = "ua.kucher.player.android"
    compileSdk = libs.versions.androidSdkCompiled.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdkMin.get().toInt()
        targetSdk = libs.versions.androidSdkTarget.get().toInt()
        applicationId = "ua.kucher.player.android"
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    signingConfigs {
//        create("release") {
//            keyAlias = ""
//            keyPassword = ""
//            storeFile = file("")
//            storePassword = ""
//        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        release {
//            isMinifyEnabled = true
//            signingConfig = signingConfigs.getByName("release")
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
        lint {
            baseline = file("lint-baseline.xml")
        }
        buildFeatures {
            buildConfig = true
        }
    }
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_21) }
}



dependencies {
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.ktx.core)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose.core)

    implementation(projects.app.common)
    implementation(projects.shared.entity)
    implementation(projects.shared.data)
}
