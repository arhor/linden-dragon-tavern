package com.github.arhor.linden.dragon.tavern.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Spell
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class SpellRepositoryImpl(
    objectMapper: ObjectMapper,
    @Value("classpath:dnd/data/5e-SRD-Spells.json")
    resource: Resource,
) : SpellRepository, DataRepositoryImplDefault<Spell, String>(
    objectMapper = objectMapper,
    resourceName = "spell",
    resourceType = Array<Spell>::class.java,
    resource = resource,
)
