package ua.kucher.player.data.albun

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Album

interface AlbumRepository {

    fun getAlbums(): Flow<List<Album>>

    fun searchAlbumsByTitle(title: String): Flow<List<Album>>

    fun getAlbumById(id: Long): Flow<Album?>

    fun getAlbumsCount(): Flow<Int>

    suspend fun fetchAlbums(): Result<Unit>

}