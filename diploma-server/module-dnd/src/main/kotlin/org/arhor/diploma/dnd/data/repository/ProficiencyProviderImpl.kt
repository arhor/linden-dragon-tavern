package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.dnd.data.model.Proficiency
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class ProficiencyProviderImpl(
    objectMapper: ObjectMapper,
) : ProficiencyProvider,
    DataProviderImplDefault<Proficiency, Proficiency, String>(
        objectMapper,
    ) {

    @Value("classpath:dnd/data/5e-SRD-Proficiencies.json")
    override lateinit var resource: Resource

    override val resourceName get() = "monster"
    override val resourceType get() = Array<Proficiency>::class.java

    override fun shrinkData(details: Proficiency): Proficiency = details
}