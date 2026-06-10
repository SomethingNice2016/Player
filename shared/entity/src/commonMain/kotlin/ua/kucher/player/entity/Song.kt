package ua.kucher.player.entity

import ua.kucher.player.core.common.bitmap.SharedBitmap

data class Song(
    val id: Long,
    val title: String,
    val duration: Long,
    val uri: String,
    val album: Album?,
    val artist: Artist?,
    val artwork: SharedBitmap?,
)
