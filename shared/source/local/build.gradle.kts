plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.sqlDelight)
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

    val xcfName = "localKit"

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
                implementation(libs.koin.core)
                implementation(libs.kotlin.stdlib)
                implementation(libs.sql.delight.runtime)
                implementation(libs.sql.delight.coroutines)

                //Project modules
                implementation(projects.shared.core.common)
                implementation(projects.shared.entity)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.sql.delight.android)
                implementation(libs.androidx.ktx.core)
            }
        }

        iosMain {
            dependencies {
               implementation(libs.sql.delight.native)
            }
        }
    }

    sqldelight {
        databases {
            create(name = "KucherPlayerDatabase") {
                packageName.set("ua.kucher.player.database")
            }
            linkSqlite = true
        }
    }
}