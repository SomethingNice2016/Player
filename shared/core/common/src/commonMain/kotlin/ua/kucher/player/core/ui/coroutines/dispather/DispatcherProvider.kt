package ua.kucher.player.core.ui.coroutines.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatcherProvider {

    companion object {
        fun create(): DispatcherProvider = DispatcherProviderImpl()
    }

    val io: CoroutineDispatcher

    val main: MainCoroutineDispatcher

    val default: CoroutineDispatcher

    val artworkCache: CoroutineDispatcher

}