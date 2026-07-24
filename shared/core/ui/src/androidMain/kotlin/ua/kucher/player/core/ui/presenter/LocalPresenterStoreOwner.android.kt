package ua.kucher.player.core.ui.presenter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView

@Composable
internal actual fun findDefaultPresenterStoreOwner(): PresenterStoreOwner? {
    return LocalView.current.findViewTreePresenterStoreOwner()
}