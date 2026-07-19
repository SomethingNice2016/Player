package ua.kucher.player.local.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song

interface SongLocalSource {

    fun getSongById(id: Long): Flow<Song?>

    fun getSongs(): Flow<List<Song>>

    fun getFavoriteSong(): Flow<List<Song>>

    fun getTopSongs(): Flow<List<Song>>

    fun getRecentlyPlayedSongs(): Flow<List<Song>>

    fun getSongsByPlaylist(playlistId: Long): Flow<List<Song>>

    fun getSongsByAlbum(albumId: Long): Flow<List<Song>>

    fun getSongsByArtist(artistId: Long): Flow<List<Song>>

    fun searchSongsByTitle(title: String): Flow<List<Song>>

    fun getSongsCount(): Flow<Int>

    fun getFavoriteSongsCount(): Flow<Int>

    fun getSongsCountByPlaylist(playlistId: Long): Flow<Int>

    suspend fun updateFavoriteTimestamp(id: Long, timestamp: Long?): Result<Unit>

    suspend fun registerPlayback(id: Long, timestamp: Long): Result<Unit>

    suspend fun fetchSongs(): Result<Unit>

}