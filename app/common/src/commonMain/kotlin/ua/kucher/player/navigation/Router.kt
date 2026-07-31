package ua.kucher.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
internal inline fun <reified T : Route> rememberRouter(
    noinline startRoute: () -> T,
): Router<T> {

    val router = rememberSaveable(
        saver = Router.Saver(startRoute),
        init = { Router(startRoute) }
    )

    DisposableEffect(router) {
        onDispose {
            router.clear()
        }
    }

    return router
}

internal class Router<T : Route> private constructor(
    private val startRoute: () -> T,
    restoredRoutes: List<T>?
) {

    companion object {
        inline fun <reified T : Route> Saver(
            noinline startRoute: () -> T
        ) = Saver<Router<T>, List<String>>(
            save = { navigator ->
                navigator.backStack.serialize()
            },
            restore = { routes ->
                Router(startRoute, routes.deserialize())
            }
        )
    }

    constructor(startRoute: () -> T) : this(startRoute, null)

    private val entries = LinkedHashMap<String, BackStackEntry<T>>()

    private val _backStack = mutableStateListOf<T>()

    val backStack: List<T>
        get() = _backStack

    val currentRoute: T
        get() = _backStack.last()

    init {
        val routes = restoredRoutes ?: listOf(startRoute())
        routes.forEach(::pushInternal)
    }

    fun getEntry(route: T): BackStackEntry<T> =
        entries.getValue(route.id)

    fun navigate(route: T) {
        pushInternal(route)
    }

    fun navigateBack() {
        if (_backStack.size <= 1)
            reset(startRoute())
        else
            removeLastEntry()
    }

    fun clear() {
        while (_backStack.isNotEmpty()) {
            removeLastEntry()
        }
    }

    fun reset(route: T) {
        clear()
        navigate(route)
    }

    private fun pushInternal(route: T) {
        entries[route.id] = BackStackEntry(route)
        _backStack.add(route)
    }

    private fun removeEntry(index: Int) {
        val route = _backStack.removeAt(index)
        entries.remove(route.id)?.close()
    }

    private fun removeLastEntry() {
        val route = _backStack.removeAt(_backStack.lastIndex)
        entries.remove(route.id)?.close()
    }
}

