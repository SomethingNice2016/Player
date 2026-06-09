package ua.kucher.player.local

import org.koin.dsl.module

val localIOSModule = module {
    single { LocalStorageSource() }
    factory { DatabaseDriverFactory() }
}