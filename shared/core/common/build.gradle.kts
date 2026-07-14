plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.android.lint)
}

kotlin {

    android {
        namespace = "ua.kucher.player.core.common"
        compileSdk {
            version = release(libs.versions.androidSdkCompiled.get().toInt()) {
                minorApiLevel = 1
            }
        }
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
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
                implementation(libs.kotlin.stdlib)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.ktx.core)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}