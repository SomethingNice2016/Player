package ua.kucher.player.songlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.datetime.TimeFormatter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

internal class SongListViewModel(
    private val timeFormatter: TimeFormatter,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        songRepository.getAllSongs(),
        playbackController.state,
        isRefreshing
    ) { songs, playbackState, refreshing ->
        SongListUiState(
            songs = songs.map { song ->
                SongUi(
                    id = song.id,
                    title = song.title,
                    artwork = song.artwork,
                    artistName = song.artist?.name ?: "",
                    duration = song.duration,
                    displayDuration = timeFormatter.toFormatDuration(song.duration)
                )
            },
            isRefreshing = refreshing,
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
            val song = songs.findLast { song -> song.id == id } ?: return@launch
            playbackController.play(
                playlist = songs,
                item = song
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            artistRepository.fetchArtists()
            albumRepository.fetchAlbums()
            songRepository.fetchSongs()
            isRefreshing.value = false
        }
    }
}