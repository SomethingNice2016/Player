plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.ksp)
}

kotlin {
    android {
        namespace = "ua.kucher.player.local"
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

    room {
        schemaDirectory("$projectDir/schemas")
    }

    val xcfName = "localKit"

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
                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.room.runtime)

                //Project modules
                implementation(projects.shared.core.common)
                implementation(projects.shared.entity)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.androidx.ktx.core)
                implementation(libs.androidx.room.sqlite.wrapper)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}