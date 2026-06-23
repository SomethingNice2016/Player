package ua.kucher.player.local.song

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName
import ua.kucher.player.local.song.entity.MergeSongsResult
import ua.kucher.player.local.song.entity.SongDto
import ua.kucher.player.local.song.entity.SongEntity
import ua.kucher.player.local.song.entity.SongWithArtwork

@Dao
internal interface SongDao {


    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME}")
    suspend fun getSongsSnapshot(): List<SongDto>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME}")
    fun getSongs(): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE albumId=:albumId")
    fun getSongsByAlbum(albumId: Long): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE artistId=:artistId")
    fun getSongsByArtist(artistId: Long): Flow<List<SongDto>>

    @Query("SELECT * FROM ${TableName.SONG_TABLE_NAME} WHERE id=:id")
    fun getSongById(id: Long): Flow<SongDto?>

    @Query("DELETE FROM ${TableName.SONG_TABLE_NAME} WHERE id=:id")
    suspend fun deleteSongById(id: Long)

    @Query("UPDATE ${TableName.SONG_TABLE_NAME} SET artwork=:artworkUri WHERE id=:id")
    suspend fun insertArtwork(id: Long, artworkUri: String)

    @Upsert
    suspend fun upsertSong(song: SongEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: SongEntity)

    @Transaction
    suspend fun insertArtworks(songsWithArtworks: List<SongWithArtwork>) {
        songsWithArtworks.forEach { entry ->
            insertArtwork(entry.songId, entry.artwork)
        }
    }

    @Transaction
    suspend fun mergeSongs(deviceSongs: List<SongEntity>): MergeSongsResult {
        val deviceIds = deviceSongs.map(SongEntity::id).toSet()
        val dbSongs = getSongsSnapshot()
        val dbSongsById = dbSongs.associateBy { dto ->
            dto.song.id
        }

        val removedIds = dbSongsById.keys - deviceIds
        val insertedSongs = mutableListOf<SongEntity>()

        removedIds.forEach { id ->
            deleteSongById(id)
        }

        deviceSongs.forEach { newSong ->
            val oldSong = dbSongsById[newSong.id]
            when {
                oldSong == null -> {
                    insertSong(newSong)
                    insertedSongs += newSong
                }

                oldSong.song.lastModified != newSong.lastModified -> {
                    upsertSong(newSong)
                }
            }
        }
        return MergeSongsResult(
            removedSongIds = removedIds,
            insertedSongs = insertedSongs
        )
    }
}