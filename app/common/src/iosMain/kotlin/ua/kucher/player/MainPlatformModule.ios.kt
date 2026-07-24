package ua.kucher.player

import org.koin.core.module.Module
import org.koin.dsl.module
import ua.kucher.player.core.common.clipboard.ClipboardController
import ua.kucher.player.core.common.clipboard.IOSClipboardController
import ua.kucher.player.core.common.share.IOSSharingManager
import ua.kucher.player.core.common.share.SharingManager
import ua.kucher.player.playback.IOSPlaybackController
import ua.kucher.player.playback.PlaybackController

internal actual val mainPlatformModule: Module = module {

    factory<PlaybackController> { get<IOSPlaybackController>() }

    single { IOSPlaybackController() }

    single<ClipboardController> { IOSClipboardController() }

    single<SharingManager> { IOSSharingManager() }

}