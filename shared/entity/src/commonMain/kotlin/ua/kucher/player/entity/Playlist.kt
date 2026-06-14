package ua.kucher.player.entity

interface Playlist {
    val items: List<Song>
    val id: Long
}