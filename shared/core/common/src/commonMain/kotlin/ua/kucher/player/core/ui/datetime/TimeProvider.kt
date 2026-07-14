package ua.kucher.player.core.ui.datetime

import kotlin.time.Clock


interface TimeProvider {

    companion object {
        fun create(): TimeProvider = SystemTimeProvider()
    }

    val currentTimestamp: Long

}

internal class SystemTimeProvider : TimeProvider {

    override val currentTimestamp: Long
        get() = Clock.System.now().toEpochMilliseconds()
}