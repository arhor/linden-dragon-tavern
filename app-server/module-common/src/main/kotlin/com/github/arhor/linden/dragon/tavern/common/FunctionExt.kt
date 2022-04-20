package com.github.arhor.linden.dragon.tavern.common

private typealias Condition<T> = (T) -> Boolean

infix fun <T> Condition<T>?.and(that: Condition<T>?): Condition<T> {
    return { (this?.invoke(it) == true) && (that?.invoke(it) == true) }
}

infix fun <T> Condition<T>?.or(that: Condition<T>?): Condition<T> {
    return { (this?.invoke(it) == true) || (that?.invoke(it) == true) }
}

operator fun <T> Condition<T>?.not(): Condition<T> {
    return { this?.invoke(it) != true }
}
