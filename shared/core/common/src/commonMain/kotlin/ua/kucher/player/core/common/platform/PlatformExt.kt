package ua.kucher.player.core.common.platform

expect val platform: Platform

val isAndroid: Boolean
    get() = platform == Platform.ANDROID

val isIOS: Boolean
    get() = platform == Platform.IOS