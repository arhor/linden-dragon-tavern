package com.github.arhor.linden.dragon.tavern.data.persistence.repository.impl;

import com.github.arhor.linden.dragon.tavern.data.persistence.repository.WithInsert
import org.springframework.data.jdbc.core.JdbcAggregateTemplate

open class WithInsertImpl<T : Any>(private val jdbcAggregateTemplate: JdbcAggregateTemplate) : WithInsert<T> {

    override fun insert(entity: T): T {
        return jdbcAggregateTemplate.insert(entity)
    }
}
