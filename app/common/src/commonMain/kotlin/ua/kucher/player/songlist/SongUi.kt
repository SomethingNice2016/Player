package ua.kucher.player.songlist

import androidx.compose.runtime.Immutable
import ua.kucher.player.core.common.bitmap.SharedBitmap

@Immutable
internal data class SongUi(
    val id: Long,
    val title: String,
    val artistName: String,
    val duration: String,
    val artwork: SharedBitmap?
)
