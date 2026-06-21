package ua.kucher.player.local.song

import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.GetSongByIdWithRelations
import ua.kucher.player.database.GetSongsByAlbumWithRelations
import ua.kucher.player.database.GetSongsByArtistWithRelations
import ua.kucher.player.database.GetSongsWithRelations
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

internal fun GetSongsWithRelations.getArtist() = ArtistEntity(
    id = artistId,
    name = artistName ?: "",
    numberOfAlbums = artistNumberOfAlbums ?: 0,
    numberOfSongs = artistNumberOfSongs ?: 0
)

internal fun GetSongsWithRelations.getAlbum() = AlbumEntity(
    id = artistId,
    title = albumTitle ?: "",
    artistId = artistId,
    artwork = albumArtwork
)

internal fun GetSongsByAlbumWithRelations.getArtist() = ArtistEntity(
    id = artistId,
    name = artistName ?: "",
    numberOfAlbums = artistNumberOfAlbums ?: 0,
    numberOfSongs = artistNumberOfSongs ?: 0
)

internal fun GetSongsByAlbumWithRelations.getAlbum() = AlbumEntity(
    id = artistId,
    title = albumTitle ?: "",
    artistId = artistId,
    artwork = albumArtwork
)

internal fun GetSongsByArtistWithRelations.getArtist() = ArtistEntity(
    id = artistId,
    name = artistName ?: "",
    numberOfAlbums = artistNumberOfAlbums ?: 0,
    numberOfSongs = artistNumberOfSongs ?: 0
)

internal fun GetSongsByArtistWithRelations.getAlbum() = AlbumEntity(
    id = artistId,
    title = albumTitle ?: "",
    artistId = artistId,
    artwork = albumArtwork
)

internal fun GetSongByIdWithRelations.getArtist() = ArtistEntity(
    id = artistId,
    name = artistName ?: "",
    numberOfAlbums = artistNumberOfAlbums ?: 0,
    numberOfSongs = artistNumberOfSongs ?: 0
)

internal fun GetSongByIdWithRelations.getAlbum() = AlbumEntity(
    id = artistId,
    title = albumTitle ?: "",
    artistId = artistId,
    artwork = albumArtwork
)

internal fun GetSongsWithRelations.toDomain(): Song {
    return SongDto(
        song = SongEntity(
            id = id,
            title = title,
            duration = duration,
            uri = uri,
            albumId = albumId,
            artistId = artistId,
            artwork = artwork,
            lastModified = lastModified,
        ),
        album = getAlbum(),
        artist = getArtist(),
    ).toDomain()
}

internal fun GetSongsByAlbumWithRelations.toDomain(): Song {
    return SongDto(
        song = SongEntity(
            id = id,
            title = title,
            duration = duration,
            uri = uri,
            albumId = albumId,
            artistId = artistId,
            artwork = artwork,
            lastModified = lastModified,
        ),
        album = getAlbum(),
        artist = getArtist(),
    ).toDomain()
}

internal fun GetSongsByArtistWithRelations.toDomain(): Song {
    return SongDto(
        song = SongEntity(
            id = id,
            title = title,
            duration = duration,
            uri = uri,
            albumId = albumId,
            artistId = artistId,
            artwork = artwork,
            lastModified = lastModified,
        ),
        album = getAlbum(),
        artist = getArtist(),
    ).toDomain()
}

internal fun GetSongByIdWithRelations.toDomain(): Song {
    return SongDto(
        song = SongEntity(
            id = id,
            title = title,
            duration = duration,
            uri = uri,
            albumId = albumId,
            artistId = artistId,
            artwork = artwork,
            lastModified = lastModified,
        ),
        album = getAlbum(),
        artist = getArtist(),
    ).toDomain()
}




