package ua.kucher.player.core.common.coroutines.dispather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatcherProvider {

    companion object {
        fun get(): DispatcherProvider = DispatcherProviderImpl()
    }

    val io: CoroutineDispatcher

    val main: MainCoroutineDispatcher

    val default: CoroutineDispatcher

    val artworkCache: CoroutineDispatcher

}