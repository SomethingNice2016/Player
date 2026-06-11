package ua.kucher.player.local

import app.cash.sqldelight.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal inline fun <reified IN : Any, reified OUT : Any> Flow<Query<IN>>.mapToList(
    crossinline mapper: suspend (IN) -> OUT,
): Flow<List<OUT>> {
    return map { query ->
        query.executeAsList()
    }.map { list ->
        list.map { item ->
            mapper(item)
        }
    }
}

internal inline fun <reified IN : Any, reified OUT : Any> Flow<Query<IN>>.mapToOneOrNull(
    crossinline mapper: suspend (IN) -> OUT,
) = map { query ->
    query.executeAsOneOrNull()?.let { entity ->
        mapper(entity)

    }
}