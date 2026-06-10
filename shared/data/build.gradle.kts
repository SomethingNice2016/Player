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

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

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
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)

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