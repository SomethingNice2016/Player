package ua.kucher.player.local.song.entity

import androidx.room.Embedded
import androidx.room.Relation
import ua.kucher.player.entity.Song
import ua.kucher.player.local.album.AlbumEntity
import ua.kucher.player.local.album.toDomain
import ua.kucher.player.local.album.toEntity
import ua.kucher.player.local.artist.ArtistEntity
import ua.kucher.player.local.artist.toDomain
import ua.kucher.player.local.artist.toEntity

internal data class SongDto(
    @Embedded
    val song: SongEntity,
    @Relation(parentColumn = "artistId", entityColumn = "id")
    val artist: ArtistEntity?,
    @Relation(parentColumn = "albumId", entityColumn = "id")
    val album: AlbumEntity?,
)

internal fun SongDto.toDomain() = Song(
    id = song.id,
    title = song.title,
    duration = song.duration,
    uri = song.uri,
    album = album?.toDomain(),
    artist = artist?.toDomain(),
    songArtwork = song.artwork,
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
        artwork = songArtwork,
        lastModified = lastModified
    ),
    artist = artist?.toEntity(),
    album = album?.toEntity(),
)


