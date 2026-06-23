package ua.kucher.player.local.song.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.kucher.player.local.TableName

@Entity(tableName = TableName.SONG_TABLE_NAME)
internal data class SongEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "duration")
    val duration: Long,
    @ColumnInfo(name = "uri")
    val uri: String,
    @ColumnInfo(name = "artwork")
    val artwork: String?,
    @ColumnInfo(name = "albumId")
    val albumId: Long,
    @ColumnInfo(name = "artistId")
    val artistId: Long,
    @ColumnInfo(name = "lastModified")
    val lastModified: Long,
)
