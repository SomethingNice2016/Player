package ua.kucher.player.entity

data class Song(
    override val id: Long,
    override val title: String,
    override val duration: Long,
    override val uri: String,
    val isFavorite: Boolean,
    val songArtwork: String?,
    val lastModified: Long,
    val album: Album?,
    val artist: Artist?,
    val playlistIds: Set<Long>,
) : PlaylistItem {

    override val artwork: String?
        get() = songArtwork ?: album?.artwork

    override val artistTitle: String?
        get() = artist?.name

    override val albumTitle: String?
        get() = album?.title

    override val albumId: Long?
        get() = album?.id

    override val artistId: Long?
        get() = artist?.id

    override val type: PlaylistItem.Type
        get() = PlaylistItem.Type.AUDIO

    interface Mapper<out T> {
        fun map(song: Song): T
    }
}
