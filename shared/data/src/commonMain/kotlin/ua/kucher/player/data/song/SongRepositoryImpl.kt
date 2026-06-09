package ua.kucher.player.data.song

import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val songLocalSource: SongLocalSource,
    private val albumLocalSource: AlbumLocalSource,
    private val artistLocalSource: ArtistLocalSource
) : SongRepository {

    override fun getSongs() = songLocalSource.getSongs()

    override suspend fun fetchSongs() = runCatching {
        songLocalSource.fetchSongs()
    }

    override suspend fun fetchAlbums() = runCatching {
        albumLocalSource.fetchAlbums()
    }

    override suspend fun fetchArtists() = runCatching {
        artistLocalSource.fetchArtists()
    }
}