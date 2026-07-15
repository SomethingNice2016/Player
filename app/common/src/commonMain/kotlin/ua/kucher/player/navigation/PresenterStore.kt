package ua.kucher.player.navigation

import kotlin.reflect.KClass

internal class PresenterStore {

    private val presenters = mutableMapOf<KClass<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getOrPut(
        clazz: KClass<T>,
        factory: () -> T
    ): T {
        return presenters.getOrPut(clazz) {
            factory()
        } as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(
        clazz: KClass<T>
    ): T? {
        return presenters[clazz] as? T
    }

    fun remove(
        clazz: KClass<*>
    ) {
        presenters.remove(clazz)
    }

    fun clear() {
        presenters.clear()
    }
}