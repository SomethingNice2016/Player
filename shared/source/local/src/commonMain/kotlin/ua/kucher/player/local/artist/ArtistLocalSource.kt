package ua.kucher.player.local.artist

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Artist

interface ArtistLocalSource {

    fun getArtistById(id: Long): Flow<Artist>

    fun getArtists(): Flow<List<Artist>>

    suspend fun fetchArtists()

}