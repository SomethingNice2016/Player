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

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} ORDER BY lastModified DESC")
    fun getSongs(): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} ORDER BY listenCount DESC LIMIT 10")
    fun getTopSongs(): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE lastPlayed != 0 ORDER BY lastPlayed DESC LIMIT 10")
    fun getRecentlyPlayedSongs(): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE favoriteAddedTime IS NOT NULL ORDER BY favoriteAddedTime DESC")
    fun getFavoriteSongs(): Flow<List<SongDto>>

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

    @Query("SELECT COUNT(*) FROM ${TableName.SONG_TABLE_NAME}")
    fun getSongsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ${TableName.SONG_TABLE_NAME} WHERE favoriteAddedTime IS NOT NULL")
    fun getFavoriteSongsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ${TableName.SONG_TABLE_NAME} s INNER JOIN ${TableName.SONG_WITH_PLAYLIST_TABLE_NAME} sp ON s.id = sp.songId WHERE sp.playlistId=:playlistId")
    fun getSongsCountByPlaylist(playlistId: Long): Flow<Int>

    @Query("UPDATE ${TableName.SONG_TABLE_NAME} SET artwork=:artworkUri WHERE id=:id")
    suspend fun setArtwork(id: Long, artworkUri: String)

    @Query("UPDATE SongEntity SET lastPlayed = :timestamp, listenCount = listenCount + 1 WHERE id = :id")
    suspend fun registerPlayback(id: Long, timestamp: Long)

    @Upsert
    suspend fun upsert(list: List<SongEntity>)

    @Query("DELETE FROM ${TableName.SONG_TABLE_NAME} WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ${TableName.SONG_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun delete(ids: List<Long>)

    @Query("UPDATE ${TableName.SONG_TABLE_NAME} SET favoriteAddedTime=:timestamp WHERE id=:id")
    suspend fun updateFavoriteTimestamp(id: Long, timestamp: Long?)

    @Transaction
    suspend fun setArtworks(songsWithArtworks: List<SongWithArtwork>) {
        songsWithArtworks.forEach { entry ->
            setArtwork(entry.songId, entry.artwork)
        }
    }

    @Transaction
    suspend fun mergeSongs(
        upsert: List<SongEntity>,
        delete: List<SongEntity>
    ) {
        delete(delete.map { it.id })
        upsert(upsert)
    }
}