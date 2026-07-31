package ua.kucher.player.song.menu

import androidx.compose.runtime.Immutable
import ua.kucher.player.common.SongUi

@Immutable
internal data class SongMenuUiState(
    val song: SongUi? = null,
    val artistId: Long? = null,
    val albumId: Long? = null
)
