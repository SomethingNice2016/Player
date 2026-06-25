package ua.kucher.player.local.song

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName
import ua.kucher.player.local.song.entity.SongDto
import ua.kucher.player.local.song.entity.SongEntity
import ua.kucher.player.local.song.entity.SongWithArtwork

@Dao
internal interface SongDao {

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME}")
    suspend fun getSongsSnapshot(): List<SongEntity>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME}")
    fun getSongs(): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE albumId=:albumId")
    fun getSongsByAlbum(albumId: Long): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE artistId=:artistId")
    fun getSongsByArtist(artistId: Long): Flow<List<SongDto>>

    @Query("SELECT s.* FROM ${TableName.SONG_TABLE_NAME} s INNER JOIN ${TableName.SONG_WITH_PLAYLIST_TABLE_NAME} sp ON s.id = sp.songId WHERE sp.playlistId=:playlistId")
    fun getSongsByPlaylist(playlistId: Long): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%'")
    fun searchSongsByTitle(title: String): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE id=:id")
    fun getSongById(id: Long): Flow<SongDto?>

    @Query("DELETE FROM ${TableName.SONG_TABLE_NAME} WHERE id=:id")
    suspend fun deleteSongById(id: Long)

    @Query("UPDATE ${TableName.SONG_TABLE_NAME} SET artwork=:artworkUri WHERE id=:id")
    suspend fun insertArtwork(id: Long, artworkUri: String)

    @Upsert
    suspend fun upsertSongs(list: List<SongEntity>)

    @Query("DELETE FROM ${TableName.SONG_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun deleteSongs(ids: List<Long>)

    @Transaction
    suspend fun insertArtworks(songsWithArtworks: List<SongWithArtwork>) {
        songsWithArtworks.forEach { entry ->
            insertArtwork(entry.songId, entry.artwork)
        }
    }

    @Transaction
    suspend fun mergeSongs(
        insert: List<SongEntity>,
        upsert: List<SongEntity>,
        delete: List<SongEntity>
    ) {
        upsertSongs(insert)
        upsertSongs(upsert)
        deleteSongs(delete.map { it.id })
    }
}