package ua.kucher.player.common

import androidx.compose.runtime.Stable
import ua.kucher.player.entity.Album

@Stable
internal data class AlbumUi(
    val id: Long,
    val title: String,
    val artwork: String?,
    val artistName: String,
    val numberOfSongs: Int,
)

internal fun Album.toUi() = AlbumUi(
    id = id,
    title = title,
    numberOfSongs = numberOfSongs,
    artwork = artwork,
    artistName = artist?.name ?: "",
)
