package ua.kucher.player.song.allsongs

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.common.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class AllSongPresenter(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController,
    private val songMapper: Song.Mapper<SongUi>,
    scope: CoroutineScope
) : Presenter(scope) {

    private val isRefreshing = MutableStateFlow(false)

    private val songs = songRepository.getSongs()
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
        AllSongUiState(
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
        initialValue = AllSongUiState()
    )

    fun playSong(id: Long) {
        scope.launch {
            val song = songRepository.getSongs().firstOrNull()?.find { song ->
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