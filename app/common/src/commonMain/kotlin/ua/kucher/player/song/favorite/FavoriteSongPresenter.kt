package ua.kucher.player.song.favorite

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class FavoriteSongPresenter(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController,
    private val songMapper: Song.Mapper<SongUi>,
) : Presenter() {

    private val isRefreshing = MutableStateFlow(false)

    private val songs = songRepository.getFavouriteSongs()
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val uiState = combine(
        songs,
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
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = FavoriteSongUiState()
    )

    fun playSong(id: Long) {
        scope.launch {
            val song = songs.value.find { song ->
                song.id == id
            } ?: return@launch

            playbackController.play(
                playlist = songs.value,
                item = song
            )
        }
    }

    fun refresh() {
        scope.launch {
            isRefreshing.value = true
            artistRepository.fetchArtists()
            albumRepository.fetchAlbums()
            songRepository.fetchSongs()
            isRefreshing.value = false
        }
    }
}