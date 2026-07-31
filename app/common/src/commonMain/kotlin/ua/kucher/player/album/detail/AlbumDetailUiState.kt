package ua.kucher.player.album.detail

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.AlbumUi
import ua.kucher.player.common.SongUi

@Immutable
internal data class AlbumDetailUiState(
    val album: AlbumUi? = null,
    val songs: List<SongUi> = emptyList(),
    val isPlaying: Boolean = false,
    val playingItemId: Long? = null
)
