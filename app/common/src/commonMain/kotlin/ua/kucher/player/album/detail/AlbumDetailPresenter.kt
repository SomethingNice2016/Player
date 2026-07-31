package ua.kucher.player.album.detail

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.SongUi
import ua.kucher.player.common.toUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class AlbumDetailPresenter(
    albumId: Long,
    albumRepository: AlbumRepository,
    songRepository: SongRepository,
    private val playbackController: PlaybackController,
    private val mapper: Song.Mapper<SongUi>
) : Presenter() {

    private val songs = songRepository.getSongsByAlbum(albumId)
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val uiState = combine(
        albumRepository.getAlbumById(albumId),
        songs,
        playbackController.state,
    ) { album, songs, playbackState ->
        AlbumDetailUiState(
            isPlaying = playbackState.isPlaying,
            playingItemId = playbackState.currentItemId,
            album = album?.toUi(),
            songs = songs.map { song ->
                mapper.map(song)
            }
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = AlbumDetailUiState()
    )

    fun play(isShuffle: Boolean = false) {
        songs.value.first().let { song ->
            playbackController.play(
                playlist = songs.value,
                item = song,
                isShuffle = isShuffle
            )
        }
    }

    fun play(songId: Long) {
        songs.value.find { song -> song.id == songId }?.let { song ->
            playbackController.play(
                playlist = songs.value,
                item = song
            )
        }
    }
}