package ua.kucher.player.local.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import ua.kucher.player.local.TableName

@Entity(
    tableName = TableName.SONG_WITH_PLAYLIST_TABLE_NAME,
    primaryKeys = ["playlistId", "songId"]
)
data class SongWithPlaylistEntity(
    @ColumnInfo(name = "playlistId")
    val playlistId: Long,
    @ColumnInfo(name = "songId")
    val songId: Long
)
