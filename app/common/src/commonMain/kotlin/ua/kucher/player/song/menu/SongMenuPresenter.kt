package ua.kucher.player.song.menu

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

internal class SongMenuPresenter(
    private val playbackController: PlaybackController,
    private val songRepository: SongRepository,
    scope: CoroutineScope
) : Presenter(scope) {

    val uiState = MutableStateFlow(SongMenuUiState())

}