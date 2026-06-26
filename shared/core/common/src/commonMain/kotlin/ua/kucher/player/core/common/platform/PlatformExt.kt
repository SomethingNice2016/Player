package ua.kucher.player.core.common.platform

expect val platform: Platform

val isAndroid: Boolean = platform == Platform.ANDROID

val isIOS: Boolean = platform == Platform.IOS