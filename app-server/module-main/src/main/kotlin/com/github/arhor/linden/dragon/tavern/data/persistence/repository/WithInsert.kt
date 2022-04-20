package com.github.arhor.linden.dragon.tavern.data.persistence.repository;

interface WithInsert<T> {

    fun insert(entity: T ): T
}
