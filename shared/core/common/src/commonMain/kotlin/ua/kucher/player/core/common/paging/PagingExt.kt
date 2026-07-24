package ua.kucher.player.core.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

private class MapPagingSource<KEY : Any, IN : Any, OUT : Any>(
    private val source: PagingSource<KEY, IN>,
    private val mapper: suspend (IN) -> OUT,
) : PagingSource<KEY, OUT>() {

    override val jumpingSupported: Boolean
        get() = source.jumpingSupported

    override val keyReuseSupported: Boolean
        get() = source.keyReuseSupported

    override fun getRefreshKey(
        state: PagingState<KEY, OUT>,
    ): KEY? = null

    override suspend fun load(
        params: LoadParams<KEY>,
    ): LoadResult<KEY, OUT> {

        return when (val result = source.load(params)) {
            is LoadResult.Error -> LoadResult.Error(result.throwable)
            is LoadResult.Invalid -> LoadResult.Invalid()
            is LoadResult.Page -> LoadResult.Page(
                data = result.data.map { mapper(it) },
                prevKey = result.prevKey,
                nextKey = result.nextKey,
                itemsBefore = result.itemsBefore,
                itemsAfter = result.itemsAfter
            )
        }
    }
}

fun <KEY : Any, IN : Any, OUT : Any> PagingSource<KEY, IN>.map(
    mapper: suspend (IN) -> OUT,
): PagingSource<KEY, OUT> = MapPagingSource(
    source = this,
    mapper = mapper
)

fun <KEY : Any, IN : Any, OUT : Any> PagingSource<KEY, IN>.mapAsync(
    mapper: suspend (IN) -> OUT
): PagingSource<KEY, OUT> = MapPagingSource(
    source = this,
    mapper = mapper
)