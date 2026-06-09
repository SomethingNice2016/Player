package ua.kucher.player.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.extensions.bottomNavPaddings

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .bottomNavPaddings()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Main screen",
            color = PlayerTheme.colorScheme.primaryTextColor,
        )
    }
}