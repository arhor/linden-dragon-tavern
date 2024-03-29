package com.github.arhor.linden.dragon.tavern.common

/**
 * Method supposed to be used for creation number bounding function,
 * which guaranties that it will return passed value only in case that
 * it is higher than bound.
 *
 * @param bound min value to be returned by created function
 * @param <N> any number type
 * @return closure with min bound value
 */
fun <N : Number> N?.minBound(bound: Int): Int {
    return this?.takeIf { it.toLong() > bound  }?.toInt() ?: bound
}

/**
 * Method supposed to be used for creation number bounding function,
 * which guaranties that it will return passed value only in case that
 * it is lower than bound.
 *
 * @param bound max value to be returned by created function
 * @param <N> any number type
 * @return closure with max bound value
 */
fun <N : Number> N?.maxBound(bound: Int): Int {
    return this?.takeIf { it.toLong() < bound }?.toInt() ?: bound
}
