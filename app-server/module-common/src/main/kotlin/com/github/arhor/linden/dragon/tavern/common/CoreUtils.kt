package com.github.arhor.linden.dragon.tavern.common

internal inline val <reified T> Iterable<T>.tail: Array<T>
    get() = drop(1).toTypedArray()

internal inline val <T> Iterable<T>.head: T
    get() = first()
