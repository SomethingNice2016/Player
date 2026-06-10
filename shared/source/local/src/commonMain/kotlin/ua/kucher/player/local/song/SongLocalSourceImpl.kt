package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ua.kucher.player.core.common.bitmap.SharedBitmap
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

    private val artworkExtractorMutex = Mutex()

    private val artworkCash = MutableStateFlow<LinkedHashMap<Long, SharedBitmap>>(LinkedHashMap())

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

    override suspend fun fetchSongs() = runCatching {
        val songsInDevice = localStorageSource.getSongs()
        songEntityQueries.deleteAllSongs()
        songsInDevice.map { song ->
            coroutineScope {
                launch {
                    songEntityQueries.insertSong(song)
                }
            }
        }.joinAll()
        val updatedMap = LinkedHashMap(artworkCash.value)
        songsInDevice.map { song ->
            coroutineScope {
                launch {
                    artworkExtractor.extractSongArtwork(song.id)?.let { artwork ->
                        artworkExtractorMutex.withLock {
                            updatedMap[song.id] = artwork
                        }
                    }
                }
            }
        }.joinAll()
        artworkCash.value = updatedMap
    }
}