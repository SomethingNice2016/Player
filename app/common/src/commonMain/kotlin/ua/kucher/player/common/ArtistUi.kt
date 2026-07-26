package ua.kucher.player.common

import androidx.compose.runtime.Immutable
import ua.kucher.player.entity.Artist

@Immutable
internal data class ArtistUi(
    val id: Long,
    val name: String,
    val artwork: String?,
    val numberOfSongs: Int,
    val numberOfAlbums: Int,
)

internal fun Artist.toUi() = ArtistUi(
    id = id,
    name = name,
    numberOfSongs = numberOfSongs,
    numberOfAlbums = numberOfAlbums,
    artwork = null,
)
