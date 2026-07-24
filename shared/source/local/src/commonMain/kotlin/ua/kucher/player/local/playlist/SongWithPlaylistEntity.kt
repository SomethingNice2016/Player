package ua.kucher.player.local.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import ua.kucher.player.local.TableName

@Entity(
    tableName = TableName.SONG_WITH_PLAYLIST_TABLE_NAME,
    primaryKeys = ["playlistId", "songId"],
    indices = [Index("playlistId"), Index("songId")]
)
data class SongWithPlaylistEntity(
    @ColumnInfo(name = "playlistId")
    val playlistId: Long,
    @ColumnInfo(name = "songId")
    val songId: Long
)
