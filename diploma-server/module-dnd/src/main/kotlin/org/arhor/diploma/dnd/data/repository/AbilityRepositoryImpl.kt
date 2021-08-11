package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.dnd.data.model.Ability
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class AbilityRepositoryImpl(
    objectMapper: ObjectMapper,
) : AbilityRepository, DataRepositoryImplDefault<Ability, String>(objectMapper) {

    @Value("classpath:dnd/data/5e-SRD-Ability-Scores.json")
    override lateinit var resource: Resource

    override val resourceName get() = "ability"
    override val resourceType get() = Array<Ability>::class.java
}
