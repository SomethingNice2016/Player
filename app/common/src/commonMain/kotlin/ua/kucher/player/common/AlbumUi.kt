package ua.kucher.player.common

import androidx.compose.runtime.Stable

@Stable
internal data class AlbumUi(
    val id: Long,
    val title: String,
    val artwork: String?,
    val artistName: String,
    val numberOfSongs: Int,
)
