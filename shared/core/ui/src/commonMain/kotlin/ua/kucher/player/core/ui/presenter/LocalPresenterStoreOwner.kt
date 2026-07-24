package ua.kucher.player.core.ui.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf

object LocalPresenterStoreOwner {
    private val LocalPresenterStoreOwner = compositionLocalOf<PresenterStoreOwner?> { null }

    val current: PresenterStoreOwner?
        @Composable get() = LocalPresenterStoreOwner.current ?: findDefaultPresenterStoreOwner()

    infix fun provides(
        presenterStoreOwner: PresenterStoreOwner
    ): ProvidedValue<PresenterStoreOwner?> {
        return LocalPresenterStoreOwner.provides(presenterStoreOwner)
    }
}

@Composable
internal expect fun findDefaultPresenterStoreOwner(): PresenterStoreOwner?
