package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import ua.kucher.player.database.SongEntityQueries

internal class SongLocalSourceImpl(
    private val songLocalScannerSource: SongLocalScannerSource,
    private val songEntityQueries: SongEntityQueries
): SongLocalSource {

    override fun getSongs() = songEntityQueries
        .getSongs().asFlow()
        .mapToList(context = Dispatchers.Unconfined)

    override suspend fun fetchSongs() {
        val songsInDevice = songLocalScannerSource.getSongs()
        songEntityQueries.deleteAllSongs()
        songsInDevice.forEach { song ->
            songEntityQueries.insertSong(song)
        }
    }

}