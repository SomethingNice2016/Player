package ua.kucher.player.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import player.app.common.generated.resources.*
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.PlayerTopAppBar
import ua.kucher.player.theme.components.PlayerTopAppBarDefaults
import ua.kucher.player.theme.extensions.bottomNavPaddings

@Composable
internal fun SettingScreen() {

    val scrollBehavior = PlayerTopAppBarDefaults.scrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = PlayerTheme.colorScheme.primaryBackground,
        topBar = {
            PlayerTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                titleRes = Res.string.setting_label,
                navigationIcon = {},
                showDivider = { false },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .bottomNavPaddings()
        ) {
            SettingItem(
                text = stringResource(Res.string.setting_item_source_code),
                painter = painterResource(Res.drawable.ic_github),
                onClick = {}
            )
            SettingItem(
                text = stringResource(Res.string.setting_item_about),
                painter = painterResource(Res.drawable.ic_about),
                onClick = {}
            )
        }
    }
}

@Composable
private fun SettingItem(
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = PlayerTheme.dimens.dimens8Px,
                horizontal = PlayerTheme.dimens.dimens16Px
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(PlayerTheme.dimens.settingItemSize),
            painter = painter,
            contentDescription = text
        )
        Spacer(modifier = Modifier.width(PlayerTheme.dimens.dimens16Px))
        Text(
            modifier = Modifier.weight(1F),
            text = text,
            color = PlayerTheme.colorScheme.primaryTextColor,
            fontStyle = PlayerTheme.typography.h5.fontStyle,
        )
    }
}