package ua.kucher.player.player

import androidx.compose.runtime.Composable
import ua.kucher.player.entity.PlaylistItem


@Composable
expect fun PlayerScreen(
    content: @Composable () -> Unit,
    item: PlaylistItem?,
    onForward: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit
)
