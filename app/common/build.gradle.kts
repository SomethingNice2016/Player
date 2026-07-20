import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
}

kotlin {
    android {
        namespace = "ua.kucher.player"
        compileSdk = libs.versions.androidSdkCompiled.get().toInt()
        minSdk = libs.versions.androidSdkMin.get().toInt()
        androidResources.enable = true
        compilerOptions { jvmTarget = JvmTarget.JVM_21 }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.resources)
            implementation(libs.compose.nav3)
            implementation(libs.kotlinx.serialization.json)

            //Permissions
            implementation(libs.permissions.core)
            implementation(libs.permissions.gallery)

            //koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose.core)

            //image loader
            implementation(libs.coil.compose)

            //Project modules
            implementation(projects.shared.data)
            implementation(projects.shared.entity)
            implementation(projects.shared.core.common)
            implementation(projects.shared.core.ui)
        }

        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.androidx.workmanager)
            implementation(libs.koin.android)
            implementation(libs.coil.http)

            //Media
            implementation(libs.androidx.media.exoplayer.core)
            implementation(libs.androidx.media.session)
            implementation(libs.androidx.media.cast)
            implementation(libs.androidx.media.ktx)
            implementation(libs.androidx.media.ui)
        }

        iosMain.dependencies {

        }

    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries {
                framework {
                    baseName = "common"
                    isStatic = true
                }
            }
        }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
