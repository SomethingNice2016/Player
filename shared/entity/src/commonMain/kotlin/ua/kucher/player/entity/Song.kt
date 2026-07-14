package ua.kucher.player.entity

data class Song(
    override val id: Long,
    override val title: String,
    override val duration: Long,
    override val uri: String,
    val songArtwork: String?,
    val lastModified: Long,
    val album: Album?,
    val artist: Artist?,
    val playlistIds: Set<Long>
) : PlaylistItem {

    override val artwork: String?
        get() = songArtwork ?: album?.artwork

    override val artistTitle: String?
        get() = artist?.name

    override val albumTitle: String?
        get() = album?.title

    interface Mapper<out T> {
        fun map(song: Song): T
    }
}
