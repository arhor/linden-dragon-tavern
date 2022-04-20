package com.github.arhor.linden.dragon.tavern.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Ability
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class AbilityRepositoryImpl(
    objectMapper: ObjectMapper,
    @Value("classpath:dnd/data/5e-SRD-Ability-Scores.json")
    resource: Resource,
) : AbilityRepository, DataRepositoryImplDefault<Ability, String>(
    objectMapper = objectMapper,
    resourceName = "ability",
    resourceType = Array<Ability>::class.java,
    resource = resource,
)
