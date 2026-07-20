package ua.kucher.player

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import ua.kucher.player.core.common.clipboard.AndroidClipboardController
import ua.kucher.player.playback.AndroidPlaybackController
import ua.kucher.player.playback.PlaybackController

internal actual val mainPlatformModule: Module = module {

    factory<PlaybackController> { get<AndroidPlaybackController>() }

    single { AndroidPlaybackController() }

    single<ua.kucher.player.core.common.clipboard.ClipboardController> {
        AndroidClipboardController(
            androidContext()
        )
    }

}