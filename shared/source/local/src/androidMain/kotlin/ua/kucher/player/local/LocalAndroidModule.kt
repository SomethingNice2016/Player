package ua.kucher.player.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localAndroidModule = module {
    single { LocalStorageSource(androidContext()) }
    factory { DatabaseDriverFactory(androidContext()) }
}