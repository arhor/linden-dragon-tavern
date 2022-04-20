package com.github.arhor.linden.dragon.tavern.common

/**
 * Generic interface for any converter implementation.
 * Since it is used basically as interface implemented by MapStruct library,
 * argument and return types marked as nullable.
 *
 * @param A entity type
 * @param B dto type
 */
interface Converter<A, B> {

    fun mapEntityToDto(item: A): B

    fun mapDtoToEntity(item: B): A
}
