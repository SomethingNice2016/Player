package ua.kucher.player.local.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song

interface SongLocalSource {

    fun getSongById(id: Long): Flow<Song?>

    fun getSongs(): Flow<List<Song>>

    fun getSongsByAlbum(albumId: Long): Flow<List<Song>>

    fun getSongsByArtist(artistId: Long): Flow<List<Song>>

    suspend fun fetchSongs(): Result<Unit>

}