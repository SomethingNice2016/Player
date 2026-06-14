package ua.kucher.player.data.song

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.entity.SongPlaylist
import ua.kucher.player.local.album.AlbumLocalSource
import ua.kucher.player.local.artist.ArtistLocalSource
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val songLocalSource: SongLocalSource,
    private val albumLocalSource: AlbumLocalSource,
    private val artistLocalSource: ArtistLocalSource
) : SongRepository {

    override fun getSongById(id: Long) = songLocalSource.getSongById(id)
        .flowOn(dispatcherProvider.io)

    override fun getAllSongs(): Flow<SongPlaylist.AllSongs> = songLocalSource.getSongs()
        .map { songs -> SongPlaylist.AllSongs(songs) }
        .flowOn(dispatcherProvider.io)

    override fun getFavouriteSongs(): Flow<SongPlaylist.FavouriteSongs> {
        TODO("Not yet implemented")
    }

    override fun getSongsByAlbum(albumId: Long) = combine(
        songLocalSource.getSongsByAlbum(albumId),
        albumLocalSource.getAlbumById(albumId)
    ) { songs, album ->
        SongPlaylist.ByAlbum(
            items = songs,
            album = album
        )
    }.flowOn(dispatcherProvider.io)

    override fun getSongsByArtist(artistId: Long) = combine(
        songLocalSource.getSongsByArtist(artistId),
        artistLocalSource.getArtistById(artistId)
    ) { songs, artist ->
        SongPlaylist.ByArtist(
            items = songs,
            artist = artist
        )
    }.flowOn(dispatcherProvider.io)

    override suspend fun fetchSongs() = withContext(dispatcherProvider.io) {
        songLocalSource.fetchSongs()
    }
}