package ua.kucher.player.core.ui.security

import java.security.MessageDigest

actual fun sha256(bytes: ByteArray): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(bytes)
        .joinToString("") { "%02x".format(it) }
}