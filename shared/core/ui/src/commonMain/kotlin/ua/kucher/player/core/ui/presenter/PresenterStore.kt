package ua.kucher.player.core.ui.presenter

import kotlin.reflect.KClass

class PresenterStore {

    private val map = mutableMapOf<KClass<out Presenter>, Presenter>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Presenter> get(klass: KClass<out T>): T? = map[klass] as? T

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter> getOrPut(
        klass: KClass<out T>,
        factory: () -> T
    ): T = map.getOrPut(klass) { factory() } as T

    fun remove(klass: KClass<out Presenter>) =
        map.remove(klass)?.clear()

    fun clear() {
        map.values.forEach { presenter ->
            presenter.clear()
        }
        map.clear()
    }
}