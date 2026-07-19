package ua.kucher.player.songplayer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.kucher.player.core.common.toBool
import ua.kucher.player.core.common.utils.ObserveOneTimeEvents
import ua.kucher.player.navigation.AppNavigator
import ua.kucher.player.song.menu.SongMenuRoute

private const val ANIMATION_DURATION_MILLIS = 500

@Composable
internal fun MusicPlayerRoute(
    modifier: Modifier = Modifier,
    presenter: MusicPlayerPresenter,
    navigator: AppNavigator,
    onPlayerExpanded: (Boolean) -> Unit,
    content: @Composable () -> Unit = {}
) {

    val state by presenter.uiState.collectAsState(null)

    val scope = rememberCoroutineScope()

    val expandPlayerProgress = remember {
        Animatable(0F)
    }

    var job: Job? by remember {
        mutableStateOf(null)
    }

    var showSongMenu by remember {
        mutableStateOf(false)
    }

    var selectedSongId by remember {
        mutableLongStateOf(0L)
    }

    fun collapsePlayer() {
        job?.cancel()
        job = scope.launch {
            expandPlayerProgress.animateTo(
                targetValue = 0F,
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS
                )
            )
        }
    }

    fun expandPlayer() {
        job?.cancel()
        job = scope.launch {
            expandPlayerProgress.animateTo(
                targetValue = 1F,
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MILLIS
                )
            )
        }
    }

    onPlayerExpanded(expandPlayerProgress.value.toBool())

    ObserveOneTimeEvents(presenter.event) { event ->
        when (event) {
            MusicPlayerEvent.CollapsePlayer -> collapsePlayer()
            MusicPlayerEvent.ExpandPlayer -> expandPlayer()
        }
    }

    MusicPlayerScreen(
        modifier = modifier,
        content = content,
        state = state,
        expandPlayerProgress = expandPlayerProgress,
        onPlay = presenter::playById,
        onForward = presenter::forward,
        onPrevious = presenter::back,
        onPlayPause = presenter::playPause,
        onShuffle = presenter::shuffle,
        onRepeat = presenter::repeat,
        onSeek = presenter::seekToPosition,
        collapsePlayer = ::collapsePlayer,
        expandPlayer = ::expandPlayer,
        onVerticalDrag = { delta ->
            job?.cancel()
            job = scope.launch {
                expandPlayerProgress.snapTo((expandPlayerProgress.value - delta).coerceIn(0F, 1F))
            }
        },
        onMenuClick = { id ->
            selectedSongId = id
            showSongMenu = true
        }
    )


    SongMenuRoute(
        songId = selectedSongId,
        showSongMenu = showSongMenu,
        navigator = navigator,
        onDismiss = {
            showSongMenu = false
        }
    )
}