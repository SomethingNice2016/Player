package ua.kucher.player.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val localPlatformModule = module {
    single { LocalStorageSource(androidContext()) }
    single { ArtworkExtractor(androidContext()) }
    factory { DatabaseDriverFactory(androidContext()) }
}