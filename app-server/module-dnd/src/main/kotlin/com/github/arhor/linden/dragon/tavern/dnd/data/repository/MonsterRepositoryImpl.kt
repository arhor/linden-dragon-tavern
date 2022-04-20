package com.github.arhor.linden.dragon.tavern.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Monster
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class MonsterRepositoryImpl(
    objectMapper: ObjectMapper,
    @Value("classpath:dnd/data/5e-SRD-Monsters.json")
    resource: Resource
) : MonsterRepository, DataRepositoryImplDefault<Monster, String>(
    objectMapper = objectMapper,
    resourceName = "monster",
    resourceType = Array<Monster>::class.java,
    resource = resource,
)
