package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.dnd.data.model.Spell
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class SpellRepositoryImpl(
    objectMapper: ObjectMapper,
) : SpellRepository, DataRepositoryImplDefault<Spell, String>(objectMapper) {

    @Value("classpath:dnd/data/5e-SRD-Spells.json")
    override lateinit var resource: Resource

    override val resourceName get() = "spell"
    override val resourceType get() = Array<Spell>::class.java
}