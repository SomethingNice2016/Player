package ua.kucher.player.data.song

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.core.common.datetime.TimeProvider
import ua.kucher.player.local.Const
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val timeProvider: TimeProvider,
    private val songLocalSource: SongLocalSource,
) : SongRepository {

    override fun getSongById(id: Long) = songLocalSource.getSongById(id)
        .flowOn(dispatcherProvider.io)

    override fun getAllSongs() = songLocalSource.getSongs()
        .flowOn(dispatcherProvider.io)

    override fun getTopSongs() = songLocalSource.getTopSongs()
        .flowOn(dispatcherProvider.io)

    override fun getRecentlyPlayedSongs() = songLocalSource.getRecentlyPlayedSongs()
        .flowOn(dispatcherProvider.io)

    override fun getFavouriteSongs() = songLocalSource.getSongsByPlaylist(Const.FAVORITE_PLAYLIST_ID)
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

    override fun getFavouriteSongsCount() = songLocalSource.getSongsCountByPlaylist(Const.FAVORITE_PLAYLIST_ID)
        .flowOn(dispatcherProvider.io)

    override suspend fun registerPlayback(id: Long) = withContext(dispatcherProvider.io) {
        songLocalSource.registerPlayback(id, timeProvider.currentTimestamp)
    }

    override suspend fun fetchSongs() = withContext(dispatcherProvider.io) {
        songLocalSource.fetchSongs()
    }
}