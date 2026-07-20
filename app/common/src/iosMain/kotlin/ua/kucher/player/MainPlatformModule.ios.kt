package ua.kucher.player

import org.koin.core.module.Module
import org.koin.dsl.module
import ua.kucher.player.core.common.clipboard.IOSClipboardController

internal actual val mainPlatformModule: Module = module {

    single<ua.kucher.player.core.common.clipboard.ClipboardController> { IOSClipboardController() }


}