package ua.kucher.player.song.menu

import androidx.compose.runtime.Stable
import ua.kucher.player.common.SongUi

@Stable
internal data class SongMenuUiState(
    val song: SongUi? = null,
    val artistId: Long? = null,
    val albumId: Long? = null
)
