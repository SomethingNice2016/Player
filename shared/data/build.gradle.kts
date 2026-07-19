plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.android.lint)
}

kotlin {
    android {
        namespace = "ua.kucher.player.data"
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

    val xcfName = "dataKit"

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
                implementation(libs.koin.core)

                //Project modules
                implementation(projects.shared.entity)
                implementation(projects.shared.source.local)
                implementation(projects.shared.core.common)
            }
        }

        androidMain {
            dependencies {

            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}