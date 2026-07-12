package ua.kucher.player.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.ArtistUi
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.coroutines.combine
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.PlaybackController

internal class HomeViewModel(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController
) : ViewModel() {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        songRepository.getSongsCount(),
        songRepository.getFavouriteSongsCount(),
        songRepository.getTopSongs(),
        artistRepository.getArtistsCount(),
        artistRepository.getTopArtists(),
        albumRepository.getAlbumsCount(),
        playbackController.state,
        isRefreshing
    ) { songsCount, favoriteSongsCount, topSongs, artistsCount, topArtists, albumCount, playbackState, isRefreshing ->
        HomeScreenUiState(
            songsCount = songsCount,
            favoriteSongsCount = favoriteSongsCount,
            artistsCount = artistsCount,
            albumCount = albumCount,
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
            isRefreshing = isRefreshing,
            isPlayerShowed = playbackState.currentItemId != null,
            topSongs = topSongs.map { song ->
                SongUi(
                    id = song.id,
                    title = song.title,
                    artistName = song.artistTitle ?: "",
                    displayDuration = "",
                    duration = song.duration,
                    artwork = song.artwork
                )
            },
            topArtists = topArtists.map { artist ->
                ArtistUi(
                    id = artist.id,
                    name = artist.name,
                    artwork = null
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeScreenUiState()
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