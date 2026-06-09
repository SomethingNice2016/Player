package ua.kucher.player.local.artist

import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.entity.Artist

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