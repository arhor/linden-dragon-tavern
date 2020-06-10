package org.arhor.diploma.service.impl

import org.arhor.diploma.domain.DomainObject
import org.arhor.diploma.repository.BaseRepository
import org.arhor.diploma.util.Converter
import java.io.Serializable

abstract class AbstractService<E : DomainObject<K>, D, K : Serializable>(
    private val converter: Converter,
    private val repository: BaseRepository<E, K>
) {

  internal inline fun <T, reified D> convert(source: T): D {
    return converter.convert(source, D::class.java)
  }
}