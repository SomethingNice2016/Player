package ua.kucher.player.songlist

import androidx.compose.runtime.Immutable

@Immutable
internal data class SongUi(
    val id: Long,
    val title: String,
    val artistName: String,
    val duration: String,
    val artwork: String?
)
