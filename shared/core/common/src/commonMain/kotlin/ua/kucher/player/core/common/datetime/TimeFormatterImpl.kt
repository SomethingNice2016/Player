package ua.kucher.player.core.common.datetime

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

internal class TimeFormatterImpl : TimeFormatter {

    companion object {
        private const val TEN_MINUTES_TIME_MILLIS = 600_000L
        private const val ONE_HOUR_TIME_MILLIS = 3_600_000L

        private const val FORMAT_UNDER_TEN_MINUTES = "m:ss"
        private const val FORMAT_UNDER_ONE_HOUR = "mm:ss"
        private const val FORMAT_UNDER_TEN_HOUR = "H:mm:ss"
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val underTenMinutesFormat = LocalTime.Format {
        byUnicodePattern(FORMAT_UNDER_TEN_MINUTES)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val underOneHourFormat = LocalTime.Format {
        byUnicodePattern(FORMAT_UNDER_ONE_HOUR)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val overOneHourFormat = LocalTime.Format {
        byUnicodePattern(FORMAT_UNDER_TEN_HOUR)
    }

    override fun toFormatDuration(duration: Long): String {

        val time = LocalTime.fromMillisecondOfDay(duration.toInt())

        return when {
            duration < TEN_MINUTES_TIME_MILLIS -> underTenMinutesFormat.format(time)
            duration < ONE_HOUR_TIME_MILLIS -> underOneHourFormat.format(time)
            else -> overOneHourFormat.format(time)
        }
    }
}