package ua.kucher.player.entity

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val audioUri: String,
    val album: Album?
)
