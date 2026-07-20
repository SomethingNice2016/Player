package ua.kucher.player.song.menu

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kucher.player.common.SongUi
import ua.kucher.player.core.ui.presenter.Presenter
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.entity.Song
import ua.kucher.player.playback.PlaybackController

internal class SongMenuPresenter(
    private val songId: Long,
    private val playbackController: PlaybackController,
    private val songRepository: SongRepository,
    private val mapper: Song.Mapper<SongUi>,
) : Presenter() {

    private val song = songRepository.getSongById(songId)
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val uiState = combine(
        song.filterNotNull(),
        playbackController.state
    ) { song, playbackState ->
        SongMenuUiState(
            song = mapper.map(song),
            artistId = song.artist?.id,
            albumId = song.album?.id
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = SongMenuUiState()
    )

    fun setFavoriteState() {
        uiState.value.song?.let { nonNullSong ->
            scope.launch {
                songRepository.setFavoriteState(
                    id = nonNullSong.id,
                    isFavorite = !nonNullSong.isFavorite
                )
            }
        }
    }

    fun playNext() {
        song.value?.let { nonNullSong ->
            playbackController.playNext(nonNullSong)
        }
    }
}