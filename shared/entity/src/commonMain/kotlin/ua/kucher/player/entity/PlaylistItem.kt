package ua.kucher.player.entity

interface PlaylistItem {

    enum class Type {
        AUDIO, VIDEO
    }

    val id: Long
    val title: String
    val duration: Long
    val uri: String
    val artwork: String?
    val artistTitle: String?
    val albumTitle: String?
    val albumId: Long?
    val artistId: Long?
    val type: Type
}

