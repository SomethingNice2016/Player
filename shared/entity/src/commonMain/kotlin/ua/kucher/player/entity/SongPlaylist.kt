package ua.kucher.player.entity

sealed class SongPlaylist(
    override val items: List<Song>,
    override val id: Long,
) : Playlist {

    data class AllSongs(
        override val items: List<Song>,
        override val id: Long = -1,
    ) : SongPlaylist(items, id)

    data class FavouriteSongs(
        override val items: List<Song>,
        override val id: Long,
    ) : SongPlaylist(items, id)

    data class ByArtist(
        override val items: List<Song>,
        val artist: Artist,
        override val id: Long = -1,
    ) : SongPlaylist(items, id)

    data class ByAlbum(
        override val items: List<Song>,
        val album: Album,
        override val id: Long = -1,
    ) : SongPlaylist(items, id)

    data class Custom(
        override val items: List<Song>,
        val names: String,
        override val id: Long,
    ) : SongPlaylist(items, id)

}