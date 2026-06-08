package ua.kucher.player.local.song

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import ua.kucher.player.database.SongEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class SongLocalScannerSource(private val context: Context) {

    companion object {
        private const val SONG_SELECTION = "${MediaStore.Audio.Media.IS_MUSIC}=1"
        private const val SONG_SORT_ORDER = "${MediaStore.Audio.Media.DATE_ADDED} DESC"
    }

    actual fun getSongs(): List<SongEntity> {
        val result = mutableListOf<SongEntity>()

        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val cursor = context.contentResolver.query(
            audioUri,
            projection,
            SONG_SELECTION,
            null,
            SONG_SORT_ORDER
        )

        cursor?.use { nonNullCursor ->

            val idCol = nonNullCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = nonNullCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = nonNullCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationCol = nonNullCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdCol = nonNullCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (nonNullCursor.moveToNext()) {

                val audioId = nonNullCursor.getLong(idCol)

                result.add(
                    SongEntity(
                        id = audioId,
                        title = nonNullCursor.getString(titleCol),
                        artist = nonNullCursor.getString(artistCol),
                        duration = nonNullCursor.getLong(durationCol),
                        uri = ContentUris.withAppendedId(audioUri, audioId).toString(),
                        albumId = nonNullCursor.getLong(albumIdCol)
                    )
                )
            }
        }
        return result
    }
}