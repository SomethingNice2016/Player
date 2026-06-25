package ua.kucher.player.local.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.kucher.player.entity.SongPlaylist
import ua.kucher.player.local.TableName


@Entity(tableName = TableName.PLAYLIST_TABLE_NAME)
internal data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        fun createFavoriteQuery() =
            "INSERT INTO ${TableName.PLAYLIST_TABLE_NAME} (id, name) VALUES (${SongPlaylist.FAVORITE_PLAYLIST_ID}, 'Favorite')"
    }
}
