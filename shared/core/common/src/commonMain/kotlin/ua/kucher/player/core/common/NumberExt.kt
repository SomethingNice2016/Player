package ua.kucher.player.core.common

fun Float.toBool(): Boolean {
    return this >= 1F
}

fun Int.toBool(): Boolean {
    return this >= 1
}

fun Long.toBool(): Boolean {
    return this >= 1L
}

fun Boolean.toFloat(): Float {
    return if (this)
        1F
    else
        0F
}

fun Boolean.toInt(): Int {
    return if (this)
        1
    else
        0
}

fun Boolean.toLong(): Long {
    return if (this)
        1L
    else
        0L
}