package org.arhor.diploma.commons

import java.util.function.Predicate
import kotlin.reflect.KProperty1

fun <T> findAll(): Predicate<T> {
    return Predicate { true }
}

fun <T, V> KProperty1<T, V>.equalTo(value: V): Predicate<T> {
    return Predicate { item ->
        this.get(item)?.equals(value) ?: (value == null)
    }
}