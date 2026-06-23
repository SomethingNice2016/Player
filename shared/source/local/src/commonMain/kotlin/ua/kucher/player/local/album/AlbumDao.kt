package ua.kucher.player.local.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName

@Dao
internal interface AlbumDao {

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME}")
    suspend fun getAlbumsSnapshot(): List<AlbumEntity>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME}")
    fun getAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME} WHERE artistId=:artistId")
    fun getAlbumsByArtist(artistId: Long): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME} WHERE id=:id")
    fun getAlbumById(id: Long): Flow<AlbumEntity?>

    @Query("DELETE FROM ${TableName.ALBUM_TABLE_NAME} WHERE id=:id")
    suspend fun deleteAlbumById(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(Album: AlbumEntity)

    @Upsert
    suspend fun upsertAlbum(Album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbums(list: List<AlbumEntity>)

    @Upsert
    suspend fun upsertAlbums(list: List<AlbumEntity>)

    @Query("DELETE FROM ${TableName.ALBUM_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun deleteAlbums(ids: List<Long>)

    @Transaction
    suspend fun mergeAlbum(
        insert: List<AlbumEntity>,
        upsert: List<AlbumEntity>,
        deleteIds: List<Long>
    ) {
        insertAlbums(insert)
        upsertAlbums(upsert)
        deleteAlbums(deleteIds)
    }

}