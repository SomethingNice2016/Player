package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.player.PlaybackController

internal class SongListViewModel(
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository,
    private val playbackController: PlaybackController
) : ViewModel() {

    val uiState: StateFlow<SongListUiState> = combine(
        songRepository.getAllSongs(),
        playbackController.currentItem,
        playbackController.isPlaying
    ) { songs, playlistItem, isPlaying ->
        SongListUiState.success(
            songs = songs.items.map { song ->
                SongUi(
                    id = song.id,
                    title = song.title,
                    artwork = song.artwork,
                    artistName = song.artist?.name ?: "",
                    duration = timeFormatter.toFormatDuration(song.duration)
                )
            },
            playingSongId = playlistItem?.id,
            isPlaying = isPlaying
        )
    }.catch {
        emit(SongListUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SongListUiState.Loading
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