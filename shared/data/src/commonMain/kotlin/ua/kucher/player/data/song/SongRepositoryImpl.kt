package ua.kucher.player.data.song

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val songLocalSource: SongLocalSource,
    private val albumLocalSource: AlbumLocalSource,
    private val artistLocalSource: ArtistLocalSource
) : SongRepository {

    override fun getSongs() = songLocalSource.getSongs()
        .flowOn(Dispatchers.IO)

    override suspend fun fetchSongs() = withContext(Dispatchers.IO) {
        runCatching {
            songLocalSource.fetchSongs()
        }
    }

    override suspend fun fetchAlbums() = withContext(Dispatchers.IO) {
        runCatching {
            albumLocalSource.fetchAlbums()
        }
    }

    override suspend fun fetchArtists() = withContext(Dispatchers.IO) {
        runCatching {
            artistLocalSource.fetchArtists()
        }
    }
}