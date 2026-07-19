plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.lint)
}

kotlin {

    android {
        namespace = "ua.kucher.player.core.ui"
        compileSdk = libs.versions.androidSdkCompiled.get().toInt()
        minSdk = libs.versions.androidSdkMin.get().toInt()

        withHostTestBuilder {

        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "shared:core:uiKit"

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.compose.runtime)
                api(libs.compose.ui)
                api(libs.compose.foundation)
                api(libs.compose.resources)
                api(libs.compose.ui.tooling.preview)
                api(libs.compose.material3)
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlin.stdlib)
                implementation(libs.kermit)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.constraintlayout.compose)
                api(libs.androidx.activityCompose)
                api(libs.kotlinx.coroutines.android)
                api(libs.androidx.ktx.core)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}