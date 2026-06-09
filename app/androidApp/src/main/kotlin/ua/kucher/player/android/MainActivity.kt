package ua.kucher.player.android

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ua.kucher.player.App
import ua.kucher.player.data.song.SongRepository

class MainActivity : ComponentActivity() {

    private val songRepository: SongRepository by inject()
    private val requestAudioPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                loadAudio()
            } else {
//                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
        checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    requiredPermission()
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                loadAudio()
            } else {
                requestAudioPermission.launch(requiredPermission())
            }
        }
    }

    private fun requiredPermission(): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }


    private fun loadAudio() {
        lifecycleScope.launch(Dispatchers.IO) {
            songRepository.fetchArtists()
            songRepository.fetchAlbums()
            songRepository.fetchSongs()
        }
    }
}