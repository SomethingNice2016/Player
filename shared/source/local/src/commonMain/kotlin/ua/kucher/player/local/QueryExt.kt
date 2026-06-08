package ua.fora.selfcheckout.local

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal inline fun <reified IN : Any, reified OUT : Any> Flow<Query<IN>>.mapToList(
    dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
    crossinline mapper: suspend (IN) -> OUT,
): Flow<List<OUT>> {
    return mapToList(dispatcher).map { list ->
        list.map { item ->
            mapper(item)
        }
    }
}

internal inline fun <reified IN : Any, reified OUT : Any> Flow<Query<IN>>.mapToOneOrNull(
    dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
    crossinline mapper: suspend (IN) -> OUT,
) = mapToOneOrNull(dispatcher).map { entity ->
    entity?.let { nonNullEntity ->
        mapper(nonNullEntity)
    }
}