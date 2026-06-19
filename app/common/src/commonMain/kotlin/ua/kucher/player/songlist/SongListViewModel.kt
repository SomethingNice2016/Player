package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.SongUi
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
        playbackController.currentItemId,
        playbackController.isPlaying
    ) { songs, currentItemId, isPlaying ->
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
            playingSongId = currentItemId,
            isPlaying = isPlaying,
            isPlayerShowed = currentItemId != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SongListUiState()
    )

    fun playSong(id: Long) {
        viewModelScope.launch {
            combine(
                songRepository.getAllSongs(),
                songRepository.getSongById(id)
            ) { songs, song ->
                Pair(songs, song)
            }.firstOrNull()?.let { (songs, song) ->
                playbackController.prepare(songs)
                playbackController.play(song)
            }
        }
    }
}