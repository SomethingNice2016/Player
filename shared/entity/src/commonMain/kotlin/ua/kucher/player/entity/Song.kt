package ua.kucher.player.entity

data class Song(
    val id: Long,
    val title: String,
    val duration: Long,
    val uri: String,
    val album: Album?,
    val artist: Artist?,
)
