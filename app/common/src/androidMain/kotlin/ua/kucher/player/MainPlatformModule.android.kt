package ua.kucher.player

import org.koin.core.module.Module
import org.koin.dsl.module
import ua.kucher.player.playback.AndroidPlaybackController
import ua.kucher.player.playback.PlaybackController

internal actual val mainPlatformModule: Module = module {

    factory<PlaybackController> { get<AndroidPlaybackController>() }

    single { AndroidPlaybackController() }

}