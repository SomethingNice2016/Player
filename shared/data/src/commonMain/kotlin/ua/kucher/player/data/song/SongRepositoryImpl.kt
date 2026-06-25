package ua.kucher.player.data.song

import kotlinx.coroutines.withContext
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.entity.SongPlaylist
import ua.kucher.player.local.song.SongLocalSource

internal class SongRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val songLocalSource: SongLocalSource,
) : SongRepository {

    override fun getSongById(id: Long) = songLocalSource.getSongById(id)

    override fun getAllSongs() = songLocalSource.getSongs()

    override fun getFavouriteSongs() = songLocalSource.getSongsByPlaylist(SongPlaylist.FAVORITE_PLAYLIST_ID)

    override fun getSongsByAlbum(albumId: Long) = songLocalSource.getSongsByAlbum(albumId)

    override fun getSongsByArtist(artistId: Long) = songLocalSource.getSongsByArtist(artistId)

    override fun getSongsByPlaylist(playlistId: Long) = songLocalSource.getSongsByPlaylist(playlistId)

    override fun searchSongsByTitle(title: String) = songLocalSource.searchSongsByTitle(title)

    override suspend fun fetchSongs() = withContext(dispatcherProvider.io) {
        songLocalSource.fetchSongs()
    }
}