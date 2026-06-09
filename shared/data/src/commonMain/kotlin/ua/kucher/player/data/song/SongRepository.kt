package ua.kucher.player.data.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song

interface SongRepository {

    fun getSongs(): Flow<List<Song>>

    suspend fun fetchSongs(): Result<Unit>

    suspend fun fetchAlbums(): Result<Unit>

    suspend fun fetchArtists(): Result<Unit>

}