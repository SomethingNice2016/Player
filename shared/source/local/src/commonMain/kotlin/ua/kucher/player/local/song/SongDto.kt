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
    val artwork: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SongDto

        if (song != other.song) return false
        if (artist != other.artist) return false
        if (album != other.album) return false
        if (!artwork.contentEquals(other.artwork)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = song.hashCode()
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (album?.hashCode() ?: 0)
        result = 31 * result + (artwork?.contentHashCode() ?: 0)
        return result
    }
}

internal fun SongDto.toDomain() = Song(
    id = song.id,
    title = song.title,
    duration = song.duration,
    uri = song.uri,
    album = album?.toDomain(),
    artist = artist?.toDomain(),
    artwork = artwork
)

internal fun Song.toDto() = SongDto(
    song = SongEntity(
        id = id,
        title = title,
        duration = duration,
        uri = uri,
        albumId = album?.id ?: -1L,
        artistId = artist?.id ?: -1L
    ),
    artist = artist?.toEntity(),
    album = album?.toEntity(),
    artwork = artwork
)


