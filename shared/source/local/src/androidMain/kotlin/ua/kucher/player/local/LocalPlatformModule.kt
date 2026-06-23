package ua.kucher.player.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual val localPlatformModule = module {
    factory { getDatabaseBuilder(androidContext()) }
    single { LocalStorageSource(androidContext()) }
    single { ArtworkCache(androidContext()) }
}