package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.dnd.data.model.Monster
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class MonsterRepositoryImpl(
    objectMapper: ObjectMapper,
) : MonsterRepository, DataRepositoryImplDefault<Monster, String>(objectMapper) {

    @Value("classpath:dnd/data/5e-SRD-Monsters.json")
    override lateinit var resource: Resource

    override val resourceName get() = "monster"
    override val resourceType get() = Array<Monster>::class.java
}