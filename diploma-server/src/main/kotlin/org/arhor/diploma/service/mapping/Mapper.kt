package org.arhor.diploma.service.mapping

interface Mapper<S, T> {

    fun map(source: S): T
}