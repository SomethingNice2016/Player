package ua.kucher.player.local.song

import kotlinx.coroutines.flow.Flow
import ua.kucher.player.database.SongEntity

interface SongLocalSource {

    fun getSongs(): Flow<List<SongEntity>>

    suspend fun fetchSongs()

}