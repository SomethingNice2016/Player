package ua.kucher.player

import org.koin.core.module.Module
import org.koin.dsl.module
import ua.kucher.player.player.PlaybackController
import ua.kucher.player.player.PlaybackControllerImpl

internal actual val mainPlatformModule: Module = module {

    single { PlaybackControllerImpl() }

    factory<PlaybackController> { get<PlaybackControllerImpl>() }
}