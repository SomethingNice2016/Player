package ua.kucher.player.local.song.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ua.kucher.player.entity.Album
import ua.kucher.player.entity.Song
import ua.kucher.player.local.album.entity.AlbumEntity
import ua.kucher.player.local.artist.ArtistEntity
import ua.kucher.player.local.artist.toDomain
import ua.kucher.player.local.playlist.PlaylistEntity
import ua.kucher.player.local.playlist.SongWithPlaylistEntity

internal data class SongDto(
    @Embedded
    val song: SongEntity,
    @Relation(parentColumn = "artistId", entityColumn = "id")
    val artist: ArtistEntity? = null,
    @Relation(parentColumn = "albumId", entityColumn = "id")
    val album: AlbumEntity? = null,
    @Relation(
        parentColumn = "id",
        entity = PlaylistEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = SongWithPlaylistEntity::class,
            parentColumn = "songId",
            entityColumn = "playlistId"
        )
    )
    val playlists: List<PlaylistEntity> = emptyList()
)

internal fun SongDto.toDomain() = Song(
    id = song.id,
    title = song.title,
    duration = song.duration,
    uri = song.uri,
    album = album?.let { nonNullAlbum ->
        Album(
            id = nonNullAlbum.id,
            title = nonNullAlbum.title,
            artwork = nonNullAlbum.artwork,
            numberOfSongs = nonNullAlbum.numberOfSongs,
            artist = artist?.toDomain()
        )
    },
    artist = artist?.toDomain(),
    songArtwork = song.artwork,
    lastModified = song.lastModified,
    isFavorite = song.favoriteAddedTime != null,
    playlistIds = playlists.map { playlist ->
        playlist.id
    }.toSet()
)


