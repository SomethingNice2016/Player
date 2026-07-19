package ua.kucher.player.local.artist

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName

@Dao
internal interface ArtistDao {

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME}")
    suspend fun getArtistsSnapshot(): List<ArtistEntity>

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME}")
    fun getArtists(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME} ORDER BY listenCount DESC LIMIT 10")
    fun getTopArtists(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME} WHERE LOWER(name) LIKE '%' || LOWER(:name) || '%'")
    fun searchArtistByName(name: String): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME} WHERE id=:id")
    fun getArtistById(id: Long): Flow<ArtistEntity?>

    @Query("SELECT COUNT(*) FROM ${TableName.ARTIST_TABLE_NAME}")
    fun getArtistsCount(): Flow<Int>

    @Query("DELETE FROM ${TableName.ARTIST_TABLE_NAME} WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ${TableName.ARTIST_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun delete(ids: List<Long>)

    @Upsert
    suspend fun upsert(artist: ArtistEntity)

    @Upsert
    suspend fun upsert(artists: List<ArtistEntity>)

    @Query("UPDATE ${TableName.ARTIST_TABLE_NAME} SET listenCount=:count WHERE id=:id")
    suspend fun updateListenCount(id: Long, count: Int)

    @Query("SELECT listenCount FROM ${TableName.ARTIST_TABLE_NAME} WHERE id=:id")
    suspend fun getListenCount(id: Long): Int

    @Transaction
    suspend fun merge(
        upsert: List<ArtistEntity>,
        delete: List<ArtistEntity>
    ) {
        upsert(upsert)
        delete(delete.map { it.id })
    }
}