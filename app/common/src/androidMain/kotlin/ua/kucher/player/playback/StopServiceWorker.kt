package ua.kucher.player.playback

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
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
        fun start(context: Context?) {
            context?.let { nonNullContext ->
                val workManager = WorkManager.getInstance(nonNullContext)
                workManager.cancelAllWorkByTag(STOP_SERVICE_WORKER)
                val workRequest = OneTimeWorkRequestBuilder<StopServiceWorker>()
                    .setInitialDelay(STOP_SERVICE_DELAY, TimeUnit.MILLISECONDS)
                    .addTag(STOP_SERVICE_WORKER)
                    .build()
                workManager.enqueue(workRequest)
            }
        }

        @JvmStatic
        fun stop(context: Context?) {
            context?.let { nonNullContext ->
                WorkManager.getInstance(nonNullContext)
                    .cancelAllWorkByTag(STOP_SERVICE_WORKER)
            }
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