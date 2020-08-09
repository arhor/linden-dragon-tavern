package org.arhor.diploma.service.mapping

/**
 * Generic interface for any converter implementation.
 * Since it is used basically as interface implemented by MapStruct library,
 * argument and return types marked as nullable.
 *
 * @param E entity type
 * @param D dto type
 */
interface Converter<E, D> {

    fun entityToDto(entity: E?): D?

    fun dtoToEntity(dto: D?): E?
}