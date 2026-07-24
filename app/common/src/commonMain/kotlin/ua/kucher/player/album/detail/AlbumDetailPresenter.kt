package ua.kucher.player.album.detail

import kotlinx.coroutines.flow.combine
import ua.kucher.player.common.SongUi
import ua.kucher.player.common.toUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song

internal class AlbumDetailPresenter(
    private val albumId: Long,
    private val albumRepository: AlbumRepository,
    private val songRepository: SongRepository,
    private val mapper: Song.Mapper<SongUi>
) : Presenter() {

    val uiState = combine(
        albumRepository.getAlbumById(albumId),
        songRepository.getSongsByAlbum(albumId)
    ) { album, songs ->
        AlbumDetailUiState(
            album = album?.toUi(),
            songs = songs.map { song ->
                mapper.map(song)
            }
        )
    }
}