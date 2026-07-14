package ua.kucher.player.song.menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

internal class SongMenuViewModel(
    private val playbackController: PlaybackController,
    private val songRepository: SongRepository
) : ViewModel() {

    val uiState = MutableStateFlow(SongMenuUiState())

}