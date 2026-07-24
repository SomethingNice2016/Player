package ua.kucher.player

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ua.kucher.player.core.common.coroutines.dispather.DispatcherProvider
import ua.kucher.player.core.ui.presenter.PresenterActivity
import ua.kucher.player.core.ui.presenter.compose.setContent
import ua.kucher.player.data.albun.AlbumRepository
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import ua.kucher.player.playback.AndroidPlaybackController
import ua.kucher.player.playback.PlaybackService
import ua.kucher.player.playback.cancelStopPlaybackService
import ua.kucher.player.playback.stopPlaybackService
import kotlin.properties.Delegates

class MainActivity : PresenterActivity() {

    private var controllerFuture: ListenableFuture<MediaController> by Delegates.notNull()

    private var controller: MediaController by Delegates.notNull()

    private val songRepository: SongRepository by inject()

    private val albumRepository: AlbumRepository by inject()

    private val artistRepository: ArtistRepository by inject()

    private val dispatcherProvider: DispatcherProvider by inject()


    private val playbackController: AndroidPlaybackController by inject()

    private val requestAudioPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (!granted.containsValue(false)) {
                loadAudio()
            } else {
                Log.w("Song", "Permissions not grated")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    initializeController()
                    awaitCancellation()
                } finally {
                    releaseController()
                }
            }
        }
        enableEdgeToEdge()
        setContent { App() }
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        cancelStopPlaybackService()
    }

    override fun onDestroy() {
        stopPlaybackService()
        super.onDestroy()
    }

    private fun checkPermission() {
        if (
            requiredPermissions().map { permission ->
                ContextCompat.checkSelfPermission(this, permission)
            }.all { result ->
                result == PackageManager.PERMISSION_GRANTED
            }
        ) {
            loadAudio()
        } else {
            requestAudioPermission.launch(requiredPermissions())
        }
    }

    private fun requiredPermissions(): Array<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }


    private fun loadAudio() {
        lifecycleScope.launch {
            albumRepository.fetchAlbums()
            artistRepository.fetchArtists()
            songRepository.fetchSongs()
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private suspend fun initializeController() {
        controllerFuture = PlaybackService.getMediaController(applicationContext)
        setController()
    }

    private suspend fun setController() {
        try {
            controller = withContext(dispatcherProvider.io) {
                controllerFuture.get()
            }
            playbackController.setController(controller)
        } catch (t: Throwable) {
            return
        }
    }


    private fun releaseController() {
        MediaController.releaseFuture(controllerFuture)
    }
}