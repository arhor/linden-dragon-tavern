package org.arhor.diploma.commons

internal inline val <reified T> Iterable<T>.tail: Array<T>
    get() = drop(1).toTypedArray()

internal inline val <T> Iterable<T>.head: T
    get() = first()