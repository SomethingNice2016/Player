package ua.kucher.player.local.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.kucher.player.local.TableName

@Entity(tableName = TableName.ALBUM_TABLE_NAME)
internal data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "artistId")
    val artistId: Long,
    @ColumnInfo(name = "artwork")
    val artwork: String?
)
