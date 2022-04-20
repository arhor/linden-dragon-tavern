package com.github.arhor.linden.dragon.tavern.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Proficiency
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class ProficiencyRepositoryImpl(
    objectMapper: ObjectMapper,
    @Value("classpath:dnd/data/5e-SRD-Proficiencies.json")
    resource: Resource
) : ProficiencyRepository, DataRepositoryImplDefault<Proficiency, String>(
    objectMapper = objectMapper,
    resourceName = "proficiency",
    resourceType = Array<Proficiency>::class.java,
    resource = resource,
)
