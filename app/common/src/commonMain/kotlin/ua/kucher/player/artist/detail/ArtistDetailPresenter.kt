package ua.kucher.player.artist.detail

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.kucher.player.common.SongUi
import ua.kucher.player.common.toUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class ArtistDetailPresenter(
    private val artistId: Long,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val playbackController: PlaybackController,
    private val mapper: Song.Mapper<SongUi>
) : Presenter() {

    private val songs = songRepository.getSongs()
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val uiState = combine(
        artistRepository.getArtistById(artistId),
        albumRepository.getAlbumsByArtist(artistId),
        songs
    ) { artist, albums, songs ->
        ArtistDetailUiState(
            artist = artist?.toUi(),
            albums = albums.map { album ->
                album.toUi()
            },
            songs = songs.map { song ->
                mapper.map(song)
            }
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = ArtistDetailUiState()
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

    fun play(id: Long) {
        songs.value.find { song -> song.id == id }?.let { song ->
            playbackController.play(
                playlist = songs.value,
                item = song
            )
        }
    }
}