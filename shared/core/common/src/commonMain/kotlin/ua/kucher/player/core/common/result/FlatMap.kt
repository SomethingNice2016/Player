package ua.kucher.player.core.common.result

suspend inline fun <reified IN, reified OUT> Result<IN>.flatMap(transform: suspend (IN) -> Result<OUT>): Result<OUT> {
    return try {
        transform(getOrThrow())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}