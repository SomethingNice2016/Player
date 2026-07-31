package ua.kucher.player.song.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import ua.kucher.player.navigation.AppRouter
import ua.kucher.player.navigation.navigateToAlbum
import ua.kucher.player.navigation.navigateToArtist
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.koinLocalPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SongMenuRoute(
    songId: Long,
    showSongMenu: Boolean,
    router: AppRouter,
    onDismiss: () -> Unit,
    showAlbumsItem: Boolean = true,
    showArtistItem: Boolean = true,
) {

    if (!showSongMenu) return

    val presenter: SongMenuPresenter = koinLocalPresenter {
        parametersOf(songId)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by presenter.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    fun dismiss() {
        scope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = ::dismiss,
        containerColor = Color.Transparent,
        sheetState = sheetState,
        dragHandle = null,
        shape = PlayerTheme.shapes.radius16Px,
        content = {
            SongMenuDialog(
                uiState = uiState,
                showArtistItem = showArtistItem,
                showAlbumsItem = showAlbumsItem,
                onBackClick = ::dismiss,
                setFavoriteState = presenter::setFavoriteState,
                onPlayNextClick = {
                    presenter.playNext()
                    dismiss()
                },
                onShareClick = {
                    presenter.share()
                },
                goToArtist = {
                    uiState.artistId?.let { artistId ->
                        dismiss()
                        router.navigateToArtist(artistId)
                    }
                },
                goToAlbum = {
                    uiState.albumId?.let { albumId ->
                        dismiss()
                        router.navigateToAlbum(albumId)
                    }
                }
            )
        }
    )
}