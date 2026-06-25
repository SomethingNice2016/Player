package ua.kucher.player.data.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song

interface SongRepository {

    fun getSongById(id: Long): Flow<Song?>

    fun getAllSongs(): Flow<List<Song>>

    fun getFavouriteSongs(): Flow<List<Song>>

    fun getSongsByAlbum(albumId: Long): Flow<List<Song>>

    fun getSongsByArtist(artistId: Long): Flow<List<Song>>

    fun getSongsByPlaylist(playlistId: Long): Flow<List<Song>>

    fun searchSongsByTitle(title: String): Flow<List<Song>>

    suspend fun fetchSongs(): Result<Unit>

}