package ua.kucher.player.local

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import ua.kucher.player.database.AlbumEntity
import ua.kucher.player.database.ArtistEntity
import ua.kucher.player.database.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class LocalStorageSource(private val context: Context) {

    companion object {
        private const val SONG_SELECTION = "${MediaStore.Audio.Media.IS_MUSIC}=1"
        private const val SONG_SORT_ORDER = "${MediaStore.Audio.Media.DATE_ADDED} DESC"
        private const val ALBUM_SORT_ORDER = "${MediaStore.Audio.Albums.ALBUM} ASC"
        private const val ARTIST_SORT_ORDER = "${MediaStore.Audio.Artists.ARTIST} ASC"
    }

    actual suspend fun getSongs(): List<SongEntity> {
        val result = mutableListOf<SongEntity>()

        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )

        context.contentResolver.query(
            audioUri,
            projection,
            SONG_SELECTION,
            null,
            SONG_SORT_ORDER
        )?.use { cursor ->

            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)
            val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()) {

                val audioId = cursor.getLong(idCol)

                result.add(
                    SongEntity(
                        id = audioId,
                        title = cursor.getString(titleCol),
                        artistId = cursor.getLong(artistIdCol),
                        duration = cursor.getLong(durationCol),
                        uri = ContentUris.withAppendedId(audioUri, audioId).toString(),
                        albumId = cursor.getLong(albumIdCol)
                    )
                )
            }
        }
        return result
    }

    actual suspend fun getAlbums(): List<AlbumEntity> {
        val result = mutableListOf<AlbumEntity>()

        // albumId -> artistId
        val albumArtistMap = mutableMapOf<Long, Long>()

        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID
            ),
            SONG_SELECTION,
            null,
            null
        )?.use { cursor ->

            val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val artistIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(albumIdCol)
                val artistId = cursor.getLong(artistIdCol)
                albumArtistMap.putIfAbsent(albumId, artistId)
            }
        }

        context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM
            ),
            null,
            null,
            ALBUM_SORT_ORDER
        )?.use { cursor ->

            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
            val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(idCol)

                result.add(
                    AlbumEntity(
                        id = albumId,
                        title = cursor.getString(titleCol),
                        artistId = albumArtistMap[albumId] ?: -1L
                    )
                )
            }
        }
        return result
    }

    actual suspend fun getArtists(): List<ArtistEntity> {
        val result = mutableListOf<ArtistEntity>()

        val projection = arrayOf(
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        )

        context.contentResolver.query(
            MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            ARTIST_SORT_ORDER
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
            val albumsCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
            val tracksCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)

            while (cursor.moveToNext()) {
                result += ArtistEntity(
                    id = cursor.getLong(idCol),
                    name = cursor.getString(nameCol),
                    numberOfAlbums = cursor.getInt(albumsCol).toLong(),
                    numberOfSongs = cursor.getInt(tracksCol).toLong()
                )
            }
        }
        return result
    }
}