package ua.kucher.player.playback

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.lifecycleScope
import androidx.media3.cast.CastPlayer
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaController
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import com.google.common.collect.ImmutableList
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import ua.kucher.player.MainActivity
import ua.kucher.player.data.artist.ArtistRepository
import ua.kucher.player.data.song.SongRepository
import kotlin.properties.Delegates

@Suppress("OPT_IN_ARGUMENT_IS_NOT_MARKER")
@UnstableApi
class PlaybackService : MediaLibraryService(),
    MediaSessionService.Listener,
    MediaLibraryService.MediaLibrarySession.Callback,
    KoinComponent {

    companion object {
        private const val SEEK_INCREMENT = 10000L
        private const val NOTIFICATION_ID = 93
        private const val CHANNEL_ID = "ua_kucher_player_notification_channel_id"

        fun getMediaController(context: Context) = MediaController.Builder(
            context.applicationContext,
            SessionToken(
                context.applicationContext,
                ComponentName(context, PlaybackService::class.java)
            ),
        ).buildAsync()

        fun stopService(context: Context?) {
            context?.let { nonNullContext ->
                nonNullContext.stopService(Intent(nonNullContext, PlaybackService::class.java))
            }
        }
    }

    private val songRepository: SongRepository by inject()

    private val artistRepository: ArtistRepository by inject()

    private val playbackAnalytics: PlaybackAnalytics by lazy {
        PlaybackAnalytics(
            songRepository = songRepository,
            artistRepository = artistRepository,
            coroutineScope = lifecycleScope
        )
    }

    private lateinit var player: Player

    private lateinit var mediaSession: MediaLibrarySession

    private var customCommands: List<CommandButton> by Delegates.notNull()

    private var customLayout = ImmutableList.of<CommandButton>()

    private val turnShuffleOnButton by lazy {
        CommandButton.Builder(CommandButton.ICON_SHUFFLE_OFF)
            .setDisplayName("")
            .setPlayerCommand(Player.COMMAND_SET_SHUFFLE_MODE, true)
            .build()
    }
    private val turnShuffleOffButton by lazy {
        CommandButton.Builder(CommandButton.ICON_SHUFFLE_ON)
            .setDisplayName("")
            .setPlayerCommand(Player.COMMAND_SET_SHUFFLE_MODE, false)
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        initializePlayer()
        initializeMediaSession()
    }

    override fun onDestroy() {
        clearListener()
        player.release()
        mediaSession.release()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (!player.isPlaying)
            stopSelf()
    }

    override fun onGetSession(p0: MediaSession.ControllerInfo): MediaLibrarySession {
        return mediaSession
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onForegroundServiceStartNotAllowedException() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) return

        val notificationManagerCompat = NotificationManagerCompat.from(this@PlaybackService)
        ensureNotificationChannel(notificationManagerCompat)
        val builder =
            NotificationCompat.Builder(this@PlaybackService, CHANNEL_ID)
                .setContentTitle("Test title")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Test big text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .also { builder -> getActivityIntent()?.let { builder.setContentIntent(it) } }
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    private fun ensureNotificationChannel(notificationManagerCompat: NotificationManagerCompat) {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_ID) != null) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "Test_channel_name",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManagerCompat.createNotificationChannel(channel)
    }

    private fun initializePlayer() {
        player = CastPlayer.Builder(applicationContext)
            .setLocalPlayer(
                ExoPlayer.Builder(this)
                    .setAudioAttributes(getAudioAttributes(), true)
                    .setSeekBackIncrementMs(SEEK_INCREMENT)
                    .setSeekForwardIncrementMs(SEEK_INCREMENT)
                    .setHandleAudioBecomingNoisy(true)
                    .build().apply {
                        addListener(playbackAnalytics)
                        addAnalyticsListener(EventLogger())
                    }
            ).build()
    }

    private fun initializeMediaSession() {
        mediaSession = MediaLibrarySession.Builder(this, player, this).also { builder ->
            getActivityIntent()?.let { nonNullIntent ->
                builder.setSessionActivity(nonNullIntent)
            }
        }.build()
        if (customLayout.isNotEmpty()) {
            mediaSession.setCustomLayout(customLayout)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setListener(this)
        }
    }

    private fun getAudioAttributes() = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()


    private fun getActivityIntent(): PendingIntent? {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntent(intent)
            getPendingIntent(0, FLAG_IMMUTABLE or FLAG_CANCEL_CURRENT)
        }
    }
}
