package ua.kucher.player.core.ui.presenter

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Suppress("DEPRECATION")
open class PresenterActivity : ComponentActivity(),
    PresenterStoreOwner {

    internal class NonConfigurationInstances {
        var presenterStore: PresenterStore? = null
    }

    private var _presenterStore: PresenterStore? = null


    override val presenterStore: PresenterStore
        get() {
            checkNotNull(application) {
                "Your activity is not yet attached to the " +
                        "Application instance. You can't request Presenter before onCreate call."
            }
            ensurePresenterStore()
            return _presenterStore!!
        }

    init {
        lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    if (!isChangingConfigurations) {
                        presenterStore.clear()
                    }
                }
            }
        )
        lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    ensurePresenterStore()
                    lifecycle.removeObserver(this)
                }
            }
        )
    }

    private fun ensurePresenterStore() {
        if (_presenterStore == null) {
            val nc = lastCustomNonConfigurationInstance as NonConfigurationInstances?
            if (nc != null) {
                _presenterStore = nc.presenterStore
            }
            if (_presenterStore == null) {
                _presenterStore = PresenterStore()
            }
        }
    }

    @Deprecated("Use a {@link androidx.lifecycle.ViewModel} to store non config state.")
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        var presenterStore = _presenterStore
        if (presenterStore == null) {

            val nc = lastCustomNonConfigurationInstance as NonConfigurationInstances?
            if (nc != null) {
                presenterStore = nc.presenterStore
            }
        }

        if (presenterStore == null) {
            return null
        }

        val nci = NonConfigurationInstances()
        nci.presenterStore = presenterStore
        return nci
    }
}