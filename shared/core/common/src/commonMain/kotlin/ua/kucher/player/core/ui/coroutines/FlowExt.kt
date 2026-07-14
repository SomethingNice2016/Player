package ua.kucher.player.core.ui.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> Flow<T?>.flatMapNotNullLatest(crossinline transform: suspend (value: T) -> Flow<R?>) =
    flatMapLatest { value ->
        if (value != null)
            transform(value)
        else
            MutableStateFlow(null)
    }

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> Flow<T?>.mapNotNull(crossinline transform: suspend (value: T) -> R) =
    map { value ->
        value?.let { notNullValue ->
            transform(notNullValue)
        }
    }


@Suppress("UNCHECKED_CAST")
fun <T0, T1, T2, T3, T4, T5, R> combine(
    flow0: Flow<T0>,
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    transform: suspend (T0, T1, T2, T3, T4, T5) -> R
): Flow<R> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4,
    flow5
) { flows ->
    transform(
        flows[0] as T0,
        flows[1] as T1,
        flows[2] as T2,
        flows[3] as T3,
        flows[4] as T4,
        flows[5] as T5
    )
}

@Suppress("UNCHECKED_CAST")
fun <T0, T1, T2, T3, T4, T5, T6, R> combine(
    flow0: Flow<T0>,
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T0, T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4,
    flow5,
    flow6
) { flows ->
    transform(
        flows[0] as T0,
        flows[1] as T1,
        flows[2] as T2,
        flows[3] as T3,
        flows[4] as T4,
        flows[5] as T5,
        flows[6] as T6
    )
}

@Suppress("UNCHECKED_CAST")
fun <T0, T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow0: Flow<T0>,
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T0, T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4,
    flow5,
    flow6,
    flow7
) { flows ->
    transform(
        flows[0] as T0,
        flows[1] as T1,
        flows[2] as T2,
        flows[3] as T3,
        flows[4] as T4,
        flows[5] as T5,
        flows[6] as T6,
        flows[7] as T7
    )
}


fun <T1, T2, R> combineNotNull(
    flow0: Flow<T1?>,
    flow1: Flow<T2?>,
    transform: suspend (T1, T2) -> R
): Flow<R?> = combine(flow0, flow1) { p0, p1 ->
    p0?.let { notNullP0 ->
        p1?.let { notNullP1 ->
            transform(notNullP0, notNullP1)
        }
    }
}

fun <T0, T1, T2, T3, R> combineNotNull(
    flow0: Flow<T0?>,
    flow1: Flow<T1?>,
    flow2: Flow<T2?>,
    flow3: Flow<T3?>,
    transform: suspend (T0, T1, T2, T3) -> R
): Flow<R?> = combine(
    flow0,
    flow1,
    flow2,
    flow3
) { p0, p1, p2, p3 ->
    p0?.let { notNullP0 ->
        p1?.let { notNullP1 ->
            p2?.let { notNullP2 ->
                p3?.let { notNullP3 ->
                    transform(
                        notNullP0,
                        notNullP1,
                        notNullP2,
                        notNullP3
                    )
                }
            }
        }
    }
}

fun <T0, T1, T2, T3, T4, R> combineNotNull(
    flow0: Flow<T0?>,
    flow1: Flow<T1?>,
    flow2: Flow<T2?>,
    flow3: Flow<T3?>,
    flow4: Flow<T4?>,
    transform: suspend (T0, T1, T2, T3, T4) -> R
): Flow<R?> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4
) { p0, p1, p2, p3, p4 ->
    p0?.let { notNullP0 ->
        p1?.let { notNullP1 ->
            p2?.let { notNullP2 ->
                p3?.let { notNullP3 ->
                    p4?.let { notNullP4 ->
                        transform(
                            notNullP0,
                            notNullP1,
                            notNullP2,
                            notNullP3,
                            notNullP4
                        )
                    }
                }
            }
        }
    }
}

fun <T0, T1, T2, T3, T4, T5, R> combineNotNull(
    flow0: Flow<T0?>,
    flow1: Flow<T1?>,
    flow2: Flow<T2?>,
    flow3: Flow<T3?>,
    flow4: Flow<T4?>,
    flow5: Flow<T5?>,
    transform: suspend (T0, T1, T2, T3, T4, T5) -> R
): Flow<R?> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4,
    flow5
) { p0, p1, p2, p3, p4, p5 ->
    p0?.let { notNullP0 ->
        p1?.let { notNullP1 ->
            p2?.let { notNullP2 ->
                p3?.let { notNullP3 ->
                    p4?.let { notNullP4 ->
                        p5?.let { notNullP5 ->
                            transform(
                                notNullP0,
                                notNullP1,
                                notNullP2,
                                notNullP3,
                                notNullP4,
                                notNullP5
                            )
                        }
                    }
                }
            }
        }
    }
}

fun <T0, T1, T2, T3, T4, T5, T6, R> combineNotNull(
    flow0: Flow<T0?>,
    flow1: Flow<T1?>,
    flow2: Flow<T2?>,
    flow3: Flow<T3?>,
    flow4: Flow<T4?>,
    flow5: Flow<T5?>,
    flow6: Flow<T6?>,
    transform: suspend (T0, T1, T2, T3, T4, T5, T6) -> R
): Flow<R?> = combine(
    flow0,
    flow1,
    flow2,
    flow3,
    flow4,
    flow5,
    flow6
) { p0, p1, p2, p3, p4, p5, p6 ->
    p0?.let { notNullP0 ->
        p1?.let { notNullP1 ->
            p2?.let { notNullP2 ->
                p3?.let { notNullP3 ->
                    p4?.let { notNullP4 ->
                        p5?.let { notNullP5 ->
                            p6?.let { notNullP6 ->
                                transform(
                                    notNullP0,
                                    notNullP1,
                                    notNullP2,
                                    notNullP3,
                                    notNullP4,
                                    notNullP5,
                                    notNullP6
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T0, T1, R> combineFlatMapLatest(
    flow0: Flow<T0>,
    flow1: Flow<T1>,
    transform: (T0, T1) -> Flow<R>
): Flow<R> = combine(
    flow0,
    flow1
) { p0, p1 ->
    Pair(p0, p1)
}.flatMapLatest { (p0, p1) ->
    transform(p0, p1)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T0, T1, T2, R> combineFlatMapLatest(
    flow0: Flow<T0>,
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    transform: (T0, T1, T2) -> Flow<R>
): Flow<R> = combine(
    flow0,
    flow1,
    flow2
) { p0, p1, p2 ->
    Triple(p0, p1, p2)
}.flatMapLatest { (p0, p1, p2) ->
    transform(p0, p1, p2)
}

fun <T1, T2, T3, R> Flow<T1>.combine(
    flow1: Flow<T2>,
    flow2: Flow<T3>,
    transform: suspend (a: T1, b: T2, c: T3) -> R
): Flow<R> = this.combine(
    combine(
        flow1,
        flow2
    ) { p2, p3 ->
        Pair(p2, p3)
    }
) { p1, (p2, p3) ->
    transform(p1, p2, p3)
}

fun <T1, T2, T3, T4, R> Flow<T1>.combine(
    flow1: Flow<T2>,
    flow2: Flow<T3>,
    flow3: Flow<T4>,
    transform: suspend (a: T1, b: T2, c: T3, d: T4) -> R
): Flow<R> = this.combine(
    combine(
        flow1,
        flow2,
        flow3
    ) { p2, p3, p4 ->
        Triple(p2, p3, p4)
    }
) { p1, (p2, p3, p4) ->
    transform(p1, p2, p3, p4)
}