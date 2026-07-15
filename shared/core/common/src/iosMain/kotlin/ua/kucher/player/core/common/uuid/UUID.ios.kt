package ua.kucher.player.core.common.uuid

import kotlin.uuid.Uuid

actual fun uuid(): String {
    return Uuid.random().toString()
}