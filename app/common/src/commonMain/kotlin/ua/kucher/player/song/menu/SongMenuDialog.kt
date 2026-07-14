package ua.kucher.player.song.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.kucher.player.theme.PlayerTheme

@Composable
internal fun SongMenuDialog(
    uiState: SongMenuUiState,
    onBackClick: () -> Unit,
    setFavoriteState: () -> Unit,
    onPlayNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlayerTheme.dimens.dimens16Px)
            .background(PlayerTheme.colorScheme.primaryBackground)
            .clip(PlayerTheme.shapes.radius16Px)
    ) {
        Box(modifier = Modifier.size(100.dp))
    }
}