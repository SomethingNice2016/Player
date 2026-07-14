package ua.kucher.player.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.kucher.player.local.album.AlbumDao
import ua.kucher.player.local.album.entity.AlbumEntity
import ua.kucher.player.local.artist.ArtistDao
import ua.kucher.player.local.artist.ArtistEntity
import ua.kucher.player.local.playlist.PlaylistEntity
import ua.kucher.player.local.playlist.SongWithPlaylistEntity
import ua.kucher.player.local.song.SongDao
import ua.kucher.player.local.song.entity.SongEntity

internal const val DATABASE_NAME = "PlayerDatabase.db"


@Database(
    entities = [
        SongEntity::class,
        ArtistEntity::class,
        AlbumEntity::class,
        PlaylistEntity::class,
        SongWithPlaylistEntity::class
    ],
    version = 1
)
internal abstract class PlayerDatabase : RoomDatabase() {

    abstract fun getSongDao(): SongDao

    abstract fun getArtistDao(): ArtistDao

    abstract fun getAlbumDao(): AlbumDao

}