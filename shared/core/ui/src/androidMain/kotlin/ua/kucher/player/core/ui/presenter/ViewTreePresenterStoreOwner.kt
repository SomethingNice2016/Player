package ua.kucher.player.core.ui.presenter

import android.view.View
import ua.kucher.player.core.ui.R

@JvmName("set")
fun View.setViewTreePresenterStoreOwner(presenterStoreOwner: PresenterStoreOwner?) {
    setTag(R.id.view_tree_presenter_store_owner, presenterStoreOwner)
}

@JvmName("get")
fun View.findViewTreePresenterStoreOwner(): PresenterStoreOwner? {
    return generateSequence(this) { view ->
        view.parent as? View
    }.firstNotNullOfOrNull { view ->
        view.getTag(R.id.view_tree_presenter_store_owner) as? PresenterStoreOwner
    }
}
