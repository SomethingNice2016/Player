package ua.kucher.player.local.artist

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Artist

interface ArtistLocalSource {

    fun getArtistById(id: Long): Flow<Artist?>

    fun getArtists(): Flow<List<Artist>>

    fun getTopArtists(): Flow<List<Artist>>

    fun getArtistsCount(): Flow<Int>

    suspend fun getListenCountById(id: Long): Result<Int>

    suspend fun updateListenCountById(id: Long, count: Int): Result<Unit>

    suspend fun fetchArtists(): Result<Unit>

}