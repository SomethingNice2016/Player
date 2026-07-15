package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ua.kucher.player.navigation.ScreenEntry

@Composable
inline fun <reified T : Any> koinPresenter(
    noinline parameters: (() -> ParametersHolder)? = null,
): T {

    val koin = getKoin()

    val scope = remember {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    DisposableEffect(scope) {
        onDispose {
            scope.cancel()
        }
    }

    return remember(scope) {
        koin.get<T> {

            val userParameters = parameters?.invoke()?.values.orEmpty()

            parametersOf(
                scope,
                *userParameters.toTypedArray()
            )
        }
    }
}


@Composable
internal inline fun <reified T : Any, reified R : NavKey> koinPresenter(
    entry: ScreenEntry<R>,
    noinline parameters: (() -> ParametersHolder)? = null,
): T {

    val koin = getKoin()

    return remember(entry, parameters) {
        entry.presenterStore.getOrPut(T::class) {

            val userParameters = parameters?.invoke()?.values.orEmpty()

            koin.get<T> {
                parametersOf(
                    entry.scope,
                    *userParameters.toTypedArray()
                )
            }
        }
    }
}