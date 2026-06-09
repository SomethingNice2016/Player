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
        //noinspection EditedTargetSdkVersion
        targetSdk = libs.versions.androidSdkTarget.get().toInt()
        applicationId = "ua.kucher.player.android"
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_21) }
}

dependencies {
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.ktx.core)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose.core)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.compose.navigation)

    implementation(projects.app.common)
    implementation(projects.shared.entity)
    implementation(projects.shared.data)
    implementation(projects.shared.source.local)
}
