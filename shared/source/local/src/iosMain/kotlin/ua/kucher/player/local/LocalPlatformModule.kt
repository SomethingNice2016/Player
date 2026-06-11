package ua.kucher.player.local

import org.koin.dsl.module

actual val localPlatformModule = module {
    single { LocalStorageSource() }
    single { ArtworkCache() }
    factory { DatabaseDriverFactory() }
}