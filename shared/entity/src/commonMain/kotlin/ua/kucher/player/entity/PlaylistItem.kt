package ua.kucher.player.entity

interface PlaylistItem {
    val id: Long
    val title: String
    val duration: Long
    val uri: String
    val artwork: String?
    val artistTitle: String?
    val albumTitle: String?
}