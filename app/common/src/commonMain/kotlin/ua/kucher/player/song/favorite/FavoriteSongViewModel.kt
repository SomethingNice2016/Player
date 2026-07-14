package ua.kucher.player.song.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class FavoriteSongViewModel(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController,
    private val songMapper: Song.Mapper<SongUi>
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        songRepository.getFavouriteSongs(),
        playbackController.state,
        isRefreshing
    ) { songs, playbackState, refreshing ->
        FavoriteSongUiState(
            songs = songs.map { song ->
                songMapper.map(song)
            },
            isRefreshing = refreshing,
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = FavoriteSongUiState()
    )

    fun playSong(id: Long) {
        viewModelScope.launch {
            val songs = songRepository.getFavouriteSongs().firstOrNull() ?: return@launch
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