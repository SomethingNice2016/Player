package ua.kucher.player.song.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.favorites
import ua.kucher.player.common.SongUi
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.screentamplates.song.SongListScreenTemplate


@Composable
internal fun FavoriteSongScreen(
    uiState: FavoriteSongUiState,
    onSongClick: (id: Long) -> Unit,
    onMenuClick: (id: Long) -> Unit,
    onRefresh: () -> Unit,
    onSearch: () -> Unit,
    onBackClick: () -> Unit
) {
    SongListScreenTemplate(
        uiState = uiState,
        title = stringResource(Res.string.favorites),
        showBackButton = true,
        onSongClick = onSongClick,
        onMenuClick = onMenuClick,
        onRefresh = onRefresh,
        onSearch = onSearch,
        onBackClick = onBackClick
    )
}

@Preview
@Composable
private fun FavoriteSongScreenPreview() {

    val songUi = SongUi(
        id = 12,
        title = "Never fade away",
        artistName = "SAMURAI",
        displayDuration = "3:33",
        duration = 69000L,
        artwork = "",
        isFavorite = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayerTheme.colorScheme.primaryBackground)
    ) {
        FavoriteSongScreen(
            uiState = FavoriteSongUiState(
                songs = listOf(
                    songUi,
                    songUi.copy(id = 13),
                    songUi.copy(id = 14),
                    songUi.copy(id = 15),
                ),
                isPlaying = true,
                playingSongId = 12L
            ),
            onSongClick = {},
            onRefresh = {},
            onSearch = {},
            onMenuClick = {},
            onBackClick = {}
        )
    }
}