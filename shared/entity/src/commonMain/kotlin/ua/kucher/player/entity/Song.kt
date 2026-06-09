package ua.kucher.player.entity

data class Song(
    val id: Long,
    val title: String,
    val duration: Long,
    val uri: String,
    val album: Album?,
    val artist: Artist?,
    val artwork: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Song

        if (id != other.id) return false
        if (duration != other.duration) return false
        if (title != other.title) return false
        if (uri != other.uri) return false
        if (album != other.album) return false
        if (artist != other.artist) return false
        if (!artwork.contentEquals(other.artwork)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + uri.hashCode()
        result = 31 * result + (album?.hashCode() ?: 0)
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (artwork?.contentHashCode() ?: 0)
        return result
    }
}
