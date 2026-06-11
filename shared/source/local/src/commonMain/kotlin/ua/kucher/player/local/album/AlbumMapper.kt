package ua.kucher.player.local.album

import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.entity.Album

internal fun AlbumEntity.toDomain() = Album(
    id = id,
    title = title,
    artistId = artistId,
    artwork = artwork
)

internal fun Album.toEntity() = AlbumEntity(
    id = id,
    title = title,
    artistId = artistId,
    artwork = artwork
)