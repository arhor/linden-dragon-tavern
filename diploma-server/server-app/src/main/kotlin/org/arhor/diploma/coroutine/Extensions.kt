@file:Suppress("UNUSED")

package org.arhor.diploma.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlin.coroutines.CoroutineContext

internal fun <T> lazyAsync(
    scope: CoroutineScope = GlobalScope,
    context: CoroutineContext = Dispatchers.Unconfined,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return scope.async(context, start = LAZY, block)
}
