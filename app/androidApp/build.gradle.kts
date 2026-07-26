import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.gms)
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
            isShrinkResources = false
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            versionNameSuffix = "-prod"
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    debugImplementation(libs.leakcanary)
    implementation(kotlin("reflect"))
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.ktx.core)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    implementation(projects.app.common)
    implementation(projects.shared.entity)
    implementation(projects.shared.data)
}
