package ua.kucher.player.data.song

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.entity.SongPlaylist
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val songLocalSource: SongLocalSource,
) : SongRepository {

    override fun getSongById(id: Long) = songLocalSource.getSongById(id)
        .flowOn(dispatcherProvider.io)

    override fun getAllSongs() = songLocalSource.getSongs()
        .flowOn(dispatcherProvider.io)

    override fun getFavouriteSongs() = songLocalSource.getSongsByPlaylist(SongPlaylist.FAVORITE_PLAYLIST_ID)
        .flowOn(dispatcherProvider.io)

    override fun getSongsByAlbum(albumId: Long) = songLocalSource.getSongsByAlbum(albumId)
        .flowOn(dispatcherProvider.io)

    override fun getSongsByArtist(artistId: Long) = songLocalSource.getSongsByArtist(artistId)
        .flowOn(dispatcherProvider.io)

    override fun getSongsByPlaylist(playlistId: Long) = songLocalSource.getSongsByPlaylist(playlistId)
        .flowOn(dispatcherProvider.io)

    override fun searchSongsByTitle(title: String) = songLocalSource.searchSongsByTitle(title)
        .flowOn(dispatcherProvider.io)

    override fun getSongsCount() = songLocalSource.getSongsCount()
        .flowOn(dispatcherProvider.io)

    override fun getFavouriteSongsCount() = songLocalSource.getSongsCountByPlaylist(SongPlaylist.FAVORITE_PLAYLIST_ID)
        .flowOn(dispatcherProvider.io)

    override suspend fun fetchSongs() = withContext(dispatcherProvider.io) {
        songLocalSource.fetchSongs()
    }
}