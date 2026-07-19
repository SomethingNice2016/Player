package ua.kucher.player.local.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.kucher.player.entity.Artist
import ua.kucher.player.local.TableName

@Entity(tableName = TableName.ARTIST_TABLE_NAME)
internal data class ArtistEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "numberOfAlbums")
    val numberOfAlbums: Int,
    @ColumnInfo(name = "numberOfSongs")
    val numberOfSongs: Int,
    @ColumnInfo(name = "listenCount")
    val listenCount: Int = 0
)

internal fun ArtistEntity.toDomain() = Artist(
    id = id,
    name = name,
    numberOfAlbums = numberOfAlbums,
    numberOfSongs = numberOfSongs,
)

internal fun Artist.toEntity() = ArtistEntity(
    id = id,
    name = name,
    numberOfAlbums = numberOfAlbums,
    numberOfSongs = numberOfSongs,
)


