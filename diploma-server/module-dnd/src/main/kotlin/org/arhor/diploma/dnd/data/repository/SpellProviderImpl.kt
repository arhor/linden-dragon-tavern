package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.dnd.data.model.Spell
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class SpellProviderImpl(
    objectMapper: ObjectMapper,
) : SpellProvider,
    DataProviderImplDefault<Spell, Spell.Details, String>(
        objectMapper,
    ) {

    @Value("classpath:dnd/data/5e-SRD-Spells.json")
    override lateinit var resource: Resource

    override val resourceName get() = "spell"
    override val resourceType get() = Array<Spell.Details>::class.java

    override fun shrinkData(details: Spell.Details) = Spell(
        name = details.name,
        level = details.level,
        school = details.school
    )
}