package ua.kucher.player.local.song

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.map
import ua.fora.selfcheckout.local.mapToList
import ua.kucher.player.database.AlbumEntityQueries
import ua.kucher.player.database.ArtisEntityQueries
import ua.kucher.player.database.SongEntityQueries
import ua.kucher.player.local.LocalStorageSource

internal class SongLocalSourceImpl(
    private val localStorageSource: LocalStorageSource,
    private val songEntityQueries: SongEntityQueries,
    private val artisEntityQueries: ArtisEntityQueries,
    private val albumEntityQueries: AlbumEntityQueries,
) : SongLocalSource {

    override fun getSongById(id: Long) = songEntityQueries
        .getSongById(id)
        .asFlow().map { query ->
            val song = query.executeAsOne()
            SongDto(
                song = song,
                album = albumEntityQueries.getAlbumById(song.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(song.artistId).executeAsOneOrNull()
            ).toDomain()
        }

    override fun getSongs() = songEntityQueries
        .getSongs()
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull()
            ).toDomain()
        }

    override fun getSongsByAlbum(albumId: Long) = songEntityQueries
        .getSongsByAlbum(albumId)
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull()
            ).toDomain()
        }

    override fun getSongsByArtist(artistId: Long) = songEntityQueries
        .getSongsByArtist(artistId)
        .asFlow()
        .mapToList { entity ->
            SongDto(
                song = entity,
                album = albumEntityQueries.getAlbumById(entity.albumId).executeAsOneOrNull(),
                artist = artisEntityQueries.getArtistById(entity.artistId).executeAsOneOrNull()
            ).toDomain()
        }

    override suspend fun fetchSongs() {
        val songsInDevice = localStorageSource.getSongs()
        songEntityQueries.deleteAllSongs()
        songsInDevice.forEach { song ->
            songEntityQueries.insertSong(song)
        }
    }
}