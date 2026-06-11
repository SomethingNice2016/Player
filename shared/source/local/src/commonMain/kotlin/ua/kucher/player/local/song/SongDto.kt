package ua.kucher.player.local.song

import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.SongEntity
import ua.kucher.player.entity.Song
import ua.kucher.player.local.album.toDomain
import ua.kucher.player.local.album.toEntity
import ua.kucher.player.local.artist.toDomain
import ua.kucher.player.local.artist.toEntity

internal data class SongDto(
    val song: SongEntity,
    val artist: ArtistEntity?,
    val album: AlbumEntity?,
)

internal fun SongDto.toDomain() = Song(
    id = song.id,
    title = song.title,
    duration = song.duration,
    uri = song.uri,
    album = album?.toDomain(),
    artist = artist?.toDomain(),
    artwork = song.artwork,
    lastModified = song.lastModified
)

internal fun Song.toDto() = SongDto(
    song = SongEntity(
        id = id,
        title = title,
        duration = duration,
        uri = uri,
        albumId = album?.id ?: -1L,
        artistId = artist?.id ?: -1L,
        artwork = artwork,
        lastModified = lastModified
    ),
    artist = artist?.toEntity(),
    album = album?.toEntity(),
)


