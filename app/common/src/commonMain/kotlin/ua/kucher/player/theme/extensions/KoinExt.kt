package ua.kucher.player.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

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