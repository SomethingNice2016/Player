package ua.kucher.player.playback

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

internal fun Activity.stopPlaybackService() {
    StopServiceWorker.start(this)
}

internal fun Activity.cancelStopPlaybackService() {
    StopServiceWorker.stop(this)
}

internal class StopServiceWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    companion object {
        private const val STOP_SERVICE_WORKER = "STOP_SERVICE_WORKER"
        private const val STOP_SERVICE_DELAY = 1000L

        @SuppressLint("VisibleForTests")
        @JvmStatic
        fun start(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<StopServiceWorker>()
                .setInitialDelay(STOP_SERVICE_DELAY, TimeUnit.MILLISECONDS)
                .addTag(STOP_SERVICE_WORKER)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    uniqueWorkName = STOP_SERVICE_WORKER,
                    existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                    request = workRequest
                )
        }

        @JvmStatic
        fun stop(context: Context) {
            WorkManager.getInstance(context)
                .cancelAllWorkByTag(STOP_SERVICE_WORKER)
        }
    }

    @OptIn(UnstableApi::class)
    override fun doWork(): Result {
        return runCatching {
            PlaybackService.stopService(appContext)
            Result.success()
        }.getOrElse {
            Result.failure()
        }
    }
}