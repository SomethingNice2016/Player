package ua.kucher.player.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.json.Json


internal interface Route : NavKey {
    val id: String

}

internal inline fun <reified T : Route> T.serialize(): String {
    return Json.encodeToString(this)
}

internal inline fun <reified T : Route> String.deserialize(): T {
    return Json.decodeFromString<T>(this)
}

internal inline fun <reified T : Route> List<T>.serialize(): List<String> {
    return map { route ->
        route.serialize()
    }
}

internal inline fun <reified T : Route> List<String>.deserialize(): List<T> {
    return map { route ->
        route.deserialize()
    }
}