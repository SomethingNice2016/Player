package ua.kucher.player.local.album

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Album

interface AlbumLocalSource {

    fun getAlbumById(id: Long): Flow<Album?>

    fun getAlbums(): Flow<List<Album>>

    fun getAlbumsByArtist(artistId: Long): Flow<List<Album>>

    fun getAlbumsCount(): Flow<Int>

    suspend fun fetchAlbums(): Result<Unit>

}