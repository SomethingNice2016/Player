package ua.kucher.player.common

import androidx.compose.runtime.Stable

@Stable
internal data class ArtistUi(
    val id: Long,
    val name: String,
    val artwork: String?,
    val numberOfSongs: Int
)
