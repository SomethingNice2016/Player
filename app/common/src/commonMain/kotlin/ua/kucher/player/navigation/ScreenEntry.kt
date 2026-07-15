package ua.kucher.player.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.reflect.KClass

internal class ScreenEntry<T : NavKey>(
    val route: T
) {

    val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    val presenterStore = PresenterStore()

    private val objects = mutableMapOf<KClass<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getOrPut(
        clazz: KClass<T>,
        factory: () -> T
    ): T {
        return objects.getOrPut(clazz) {
            factory()
        } as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(
        clazz: KClass<T>
    ): T? {
        return objects[clazz] as? T
    }

    fun remove(
        clazz: KClass<*>
    ) {
        objects.remove(clazz)
    }

    fun clear() {
        presenterStore.clear()
        objects.clear()
        scope.cancel()
    }
}