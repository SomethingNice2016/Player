package ua.kucher.player.data.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song

interface SongRepository {

    fun getSongById(id: Long): Flow<Song>

    fun getSongs(): Flow<List<Song>>

    fun getSongsByAlbum(albumId: Long): Flow<List<Song>>

    fun getSongsByArtist(artistId: Long): Flow<List<Song>>

    suspend fun fetchSongs(): Result<Unit>

}