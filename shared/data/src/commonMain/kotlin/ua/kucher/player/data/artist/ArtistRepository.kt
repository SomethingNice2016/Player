package ua.kucher.player.data.artist

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Artist

interface ArtistRepository {

    fun getArtists(): Flow<List<Artist>>

    fun getArtistById(id: Long): Flow<Artist?>

    suspend fun fetchArtists(): Result<Unit>

}