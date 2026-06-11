package ua.kucher.player.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val localPlatformModule = module {
    single { LocalStorageSource(androidContext()) }
    single { ArtworkCache(androidContext()) }
    factory { DatabaseDriverFactory(androidContext()) }
}