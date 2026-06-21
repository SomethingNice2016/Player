package ua.kucher.player.common

import androidx.compose.runtime.Stable

@Stable
internal data class SongUi(
    val id: Long,
    val title: String,
    val artistName: String,
    val displayDuration: String,
    val duration: Long,
    val artwork: String?
)