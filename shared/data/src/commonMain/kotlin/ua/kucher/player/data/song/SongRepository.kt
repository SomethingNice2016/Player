package ua.kucher.player.data.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.entity.Song
import ua.kucher.player.entity.SongPlaylist

interface SongRepository {

    fun getSongById(id: Long): Flow<Song>

    fun getAllSongs(): Flow<SongPlaylist.AllSongs>

    fun getFavouriteSongs(): Flow<SongPlaylist.FavouriteSongs>

    fun getSongsByAlbum(albumId: Long): Flow<SongPlaylist.ByAlbum>

    fun getSongsByArtist(artistId: Long): Flow<SongPlaylist.ByArtist>

    suspend fun fetchSongs(): Result<Unit>

}