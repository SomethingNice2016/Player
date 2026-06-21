package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

internal class SongListViewModel(
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository,
    private val playbackController: PlaybackController
) : ViewModel() {

    val uiState = combine(
        songRepository.getAllSongs(),
        playbackController.state,
    ) { songs, playbackState ->
        SongListUiState(
            songs = songs.items.map { song ->
                SongUi(
                    id = song.id,
                    title = song.title,
                    artwork = song.artwork,
                    artistName = song.artist?.name ?: "",
                    duration = song.duration,
                    displayDuration = timeFormatter.toFormatDuration(song.duration)
                )
            },
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
            isPlayerShowed = playbackState.currentItemId != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SongListUiState()
    )

    fun playSong(id: Long) {
        viewModelScope.launch {
            val songs = songRepository.getAllSongs().firstOrNull() ?: return@launch
            val song = songRepository.getSongById(id).firstOrNull() ?: return@launch
            if (playbackController.state.value.currentPlaylistId != songs.id) {
                playbackController.prepare(songs)
            }
            playbackController.play(song)
        }
    }
}