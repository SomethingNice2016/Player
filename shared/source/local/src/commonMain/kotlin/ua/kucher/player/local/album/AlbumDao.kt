package ua.kucher.player.local.album

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName
import ua.kucher.player.local.album.entity.AlbumDto
import ua.kucher.player.local.album.entity.AlbumEntity

@Dao
internal interface AlbumDao {

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME}")
    suspend fun getAlbumsSnapshot(): List<AlbumEntity>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME}")
    fun getAlbums(): Flow<List<AlbumDto>>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME} WHERE artistId=:artistId")
    fun getAlbumsByArtist(artistId: Long): Flow<List<AlbumDto>>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME} WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%'")
    fun searchAlbumsByTitle(title: String): Flow<List<AlbumDto>>

    @Query("SELECT * FROM ${TableName.ALBUM_TABLE_NAME} WHERE id=:id")
    fun getAlbumById(id: Long): Flow<AlbumDto?>

    @Query("SELECT COUNT(*) FROM ${TableName.ALBUM_TABLE_NAME}")
    fun getAlbumsCount(): Flow<Int>

    @Query("DELETE FROM ${TableName.ALBUM_TABLE_NAME} WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ${TableName.ALBUM_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun delete(ids: List<Long>)

    @Upsert
    suspend fun upsert(Album: AlbumEntity)

    @Upsert
    suspend fun upsert(list: List<AlbumEntity>)

    @Transaction
    suspend fun merge(
        upsert: List<AlbumEntity>,
        deleteIds: List<AlbumEntity>
    ) {
        upsert(upsert)
        delete(deleteIds.map { it.id })
    }

}