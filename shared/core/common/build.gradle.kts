plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.android.lint)
}

kotlin {

    android {
        namespace = "ua.kucher.player.core.common"
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

    val xcfName = "shared:core:commonKit"

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
                api(kotlin("reflect"))
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
                api(libs.kotlin.stdlib)
                api(libs.paging.common)
                api(libs.kermit)
                api(libs.okio)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.ktx.core)
                api(libs.kotlinx.coroutines.android)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}