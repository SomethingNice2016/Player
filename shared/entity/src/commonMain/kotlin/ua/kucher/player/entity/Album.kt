package ua.kucher.player.entity

data class Album(
    val id: Long,
    val title: String,
    val numberOfSongs: Int,
    val artwork: String?,
    val artist: Artist?,
)