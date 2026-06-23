package ua.kucher.player.local.artist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("SELECT * FROM ${TableName.ARTIST_TABLE_NAME} WHERE id=:id")
    fun getArtistById(id: Long): Flow<ArtistEntity?>

    @Query("DELETE FROM ${TableName.ARTIST_TABLE_NAME} WHERE id=:id")
    suspend fun deleteArtistById(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtist(artist: ArtistEntity)

    @Upsert
    suspend fun upsertArtist(artist: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtists(list: List<ArtistEntity>)

    @Upsert
    suspend fun upsertArtists(list: List<ArtistEntity>)

    @Query("DELETE FROM ${TableName.ARTIST_TABLE_NAME} WHERE id IN (:ids)")
    suspend fun deleteArtists(ids: List<Long>)

    @Transaction
    suspend fun mergeArtist(
        insert: List<ArtistEntity>,
        upsert: List<ArtistEntity>,
        delete: List<ArtistEntity>
    ) {
        insertArtists(insert)
        upsertArtists(upsert)
        deleteArtists(delete.map { it.id })
    }
}