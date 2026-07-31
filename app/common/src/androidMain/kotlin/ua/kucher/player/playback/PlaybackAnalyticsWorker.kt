package ua.kucher.player.playback

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository

class PlaybackAnalyticsWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    companion object {

        private const val DEFAULT = -1L
        private const val PLAYBACK_ANALYTICS_WORKER = "PLAYBACK_ANALYTICS_WORKER"
        private const val ARTIST_ID_KEY = "ARTIST_ID_KEY"
        private const val SONG_ID_KEY = "SONG_ID_KEY"

        fun start(
            context: Context,
            songId: Long,
            artistId: Long?
        ) {
            val data = Data.Builder()
                .putLong(SONG_ID_KEY, songId)
                .putLong(ARTIST_ID_KEY, artistId ?: DEFAULT)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<PlaybackAnalyticsWorker>()
                .addTag(PLAYBACK_ANALYTICS_WORKER)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    uniqueWorkName = PLAYBACK_ANALYTICS_WORKER,
                    existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                    request = workRequest
                )
        }
    }

    private val songRepository: SongRepository by inject()

    private val artistRepository: ArtistRepository by inject()


    override suspend fun doWork(): Result {
        val songId = inputData.getLong(SONG_ID_KEY, DEFAULT).takeIf { id ->
            id != DEFAULT
        } ?: return Result.failure()

        songRepository.registerPlayback(songId)

        inputData.getLong(ARTIST_ID_KEY, DEFAULT).takeIf { id ->
            id != DEFAULT
        }?.let { id ->
            artistRepository.incListenCount(id)
        }

        return Result.success()
    }
}