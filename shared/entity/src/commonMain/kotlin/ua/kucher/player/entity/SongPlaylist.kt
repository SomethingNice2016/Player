package ua.kucher.player.entity

sealed class SongPlaylist(
    override val items: List<Song>,
    override val id: Long,
) : Playlist {

    companion object {
        const val ALL_PLAYLIST_ID = -1L
        const val BY_ARTIST_PLAYLIST_ID = -2L
        const val BY_ALBUM_PLAYLIST_ID = -3L
        const val FAVORITE_PLAYLIST_ID = 1L
    }

    data class AllSongs(
        override val items: List<Song>,
    ) : SongPlaylist(items, ALL_PLAYLIST_ID)

    data class FavouriteSongs(
        override val items: List<Song>,
    ) : SongPlaylist(items, FAVORITE_PLAYLIST_ID)

    data class ByArtist(
        override val items: List<Song>,
        val artist: Artist,
    ) : SongPlaylist(items, BY_ARTIST_PLAYLIST_ID)

    data class ByAlbum(
        override val items: List<Song>,
        val album: Album,
    ) : SongPlaylist(items, BY_ALBUM_PLAYLIST_ID)

    data class Custom(
        override val items: List<Song>,
        val names: String,
        override val id: Long,
    ) : SongPlaylist(items, id)

}