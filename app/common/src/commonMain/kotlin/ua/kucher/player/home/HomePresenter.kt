package ua.kucher.player.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.common.toUi
import ua.kucher.player.core.common.coroutines.combine
import ua.kucher.player.core.common.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class HomePresenter(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController,
    private val songMapper: Song.Mapper<SongUi>,
    scope: CoroutineScope
) : Presenter(scope) {

    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        songRepository.getSongsCount(),
        songRepository.getFavouriteSongsCount(),
        songRepository.getRecentlyPlayedSongs(),
        artistRepository.getArtistsCount(),
        artistRepository.getTopArtists(),
        albumRepository.getAlbumsCount(),
        playbackController.state,
        isRefreshing
    ) { songsCount,
        favoriteSongsCount,
        recentlyPlayedSongs,
        artistsCount,
        topArtists,
        albumCount,
        playbackState,
        isRefreshing ->

        HomeScreenUiState(
            songsCount = songsCount,
            favoriteSongsCount = favoriteSongsCount,
            artistsCount = artistsCount,
            albumCount = albumCount,
            playingSongId = playbackState.currentItemId,
            isPlaying = playbackState.isPlaying,
            isRefreshing = isRefreshing,
            recentlyPlayedSongs = recentlyPlayedSongs.map { song ->
                songMapper.map(song)
            },
            topArtists = topArtists.map { artist ->
                artist.toUi()
            }
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = HomeScreenUiState()
    )

    fun playSong(id: Long) {
        scope.launch {
            val songs = songRepository.getSongs().firstOrNull() ?: return@launch
            val song = songs.findLast { song -> song.id == id } ?: return@launch
            playbackController.play(
                playlist = songs,
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