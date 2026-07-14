package ua.kucher.player.local.album.entity

import androidx.room.Embedded
import androidx.room.Relation
import ua.kucher.player.entity.Album
import ua.kucher.player.local.artist.ArtistEntity
import ua.kucher.player.local.artist.toDomain

internal data class AlbumDto(
    @Embedded
    val album: AlbumEntity,
    @Relation(parentColumn = "artistId", entityColumn = "id")
    val artist: ArtistEntity?,
)

internal fun AlbumDto.toDomain() =
    Album(
        id = album.id,
        title = album.title,
        artwork = album.artwork,
        numberOfSongs = album.numberOfSongs,
        artist = artist?.toDomain(),
    )
