package ua.kucher.player.local.playlist

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.kucher.player.local.TableName

@Dao
internal interface PlaylistDao {

    @Query("SELECT * FROM ${TableName.PLAYLIST_TABLE_NAME}")
    fun getPlaylist(): Flow<List<PlaylistEntity>>

}