package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.database.SongEntityQueries
import ua.kucher.player.local.ArtworkExtractor
import ua.kucher.player.local.LocalStorageSource

internal class SongLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val artworkExtractor: ArtworkExtractor,
    private val songEntityQueries: SongEntityQueries,
    private val artisEntityQueries: ArtisEntityQueries,
    private val albumEntityQueries: AlbumEntityQueries,
) : SongLocalSource {

    private val artworkCash = MutableStateFlow<LinkedHashMap<Long, ByteArray>>(LinkedHashMap())

    override fun getSongById(id: Long) = combine(
        songEntityQueries.getSongById(id).asFlow(),
        artworkCash
    ) { query, artworks ->
        val song = query.executeAsOne()
        SongDto(
            song = song,
            album = albumEntityQueries.getAlbumById(song.albumId).executeAsOneOrNull(),
            artist = artisEntityQueries.getArtistById(song.artistId).executeAsOneOrNull(),
            artwork = artworks[song.id]
        ).toDomain()
    }

    override fun getSongs() = combine(
        songEntityQueries.getSongs().asFlow(),
        artworkCash
    ) { query, artworks ->
        query.executeAsList().map { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
                artwork = artworks[entity.id]
            ).toDomain()
        }
    }

    override fun getSongsByAlbum(albumId: Long) = combine(
        songEntityQueries.getSongsByAlbum(albumId).asFlow(),
        artworkCash
    ) { query, artworks ->
        query.executeAsList().map { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
                artwork = artworks[entity.id]
            ).toDomain()
        }
    }

    override fun getSongsByArtist(artistId: Long) = combine(
        songEntityQueries.getSongsByArtist(artistId).asFlow(),
        artworkCash
    ) { query, artworks ->
        query.executeAsList().map { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull(),
                artwork = artworks[entity.id]
            ).toDomain()
        }
    }

    override suspend fun fetchSongs() {
        val songsInDevice = localStorageSource.getSongs()
        songEntityQueries.deleteAllSongs()
        songsInDevice.forEach { song ->
            songEntityQueries.insertSong(song)
        }
        val updatedMap = LinkedHashMap(artworkCash.value)
        songsInDevice.forEach { song ->
            artworkExtractor.extractSongArtwork(song.id)?.let { artwork ->
                updatedMap[song.id] = artwork
            }
        }
        artworkCash.value = updatedMap
    }
}