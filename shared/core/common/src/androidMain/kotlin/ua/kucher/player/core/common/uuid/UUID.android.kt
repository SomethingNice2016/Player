package ua.kucher.player.core.common.uuid

import java.util.*

actual fun uuid(): String {
    return UUID.randomUUID().toString()
}