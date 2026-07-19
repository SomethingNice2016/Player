package ua.kucher.player.song.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.core.parameter.parametersOf
import ua.kucher.player.navigation.AppNavigator
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.koinPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SongMenuRoute(
    songId: Long,
    showSongMenu: Boolean,
    navigator: AppNavigator,
    onDismiss: () -> Unit,
) {

    if (!showSongMenu) return

    val presenter: SongMenuPresenter = koinPresenter {
        parametersOf(songId)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by presenter.uiState.collectAsState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        containerColor = Color.Transparent,
        sheetState = sheetState,
        dragHandle = null,
        shape = PlayerTheme.shapes.radius16Px,
        content = {
            SongMenuDialog(
                uiState = uiState,
                onBackClick = onDismiss,
                setFavoriteState = presenter::setFavoriteState,
                onPlayNextClick = {
                    presenter.playNext()
                    onDismiss()
                },
                onShareClick = {

                },
                goToArtist = {
                    uiState.artistId?.let { id ->
                        onDismiss()
                    }
                },
                goToAlbum = {
                    uiState.albumId?.let { id ->
                        onDismiss()
                    }
                }
            )
        }
    )
}