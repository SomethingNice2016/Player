package ua.kucher.player.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun rememberScreenSizeHeight() = LocalConfiguration.current.screenHeightDp.dp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun rememberScreenSizeWidth() = LocalConfiguration.current.screenWidthDp.dp