package ua.kucher.player.core.common.datetime

interface TimeFormatter {

    companion object {
        fun get(): TimeFormatter = TimeFormatterImpl()
    }

    fun toFormatDuration(duration: Long): String
}