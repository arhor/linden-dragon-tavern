package org.arhor.diploma.util

import java.util.function.Predicate
import kotlin.reflect.KProperty1

internal fun <T> findAll(): Predicate<T> {
    return Predicate { true }
}

internal fun <T, V> KProperty1<T, V>.equalTo(value: V): Predicate<T> {
    return Predicate { item ->
        this.get(item)?.equals(value) ?: (value == null)
    }
}