package org.arhor.diploma.service.impl

import org.arhor.diploma.service.dto.Ability
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AbilityService(loader: ResourceLoader) : DataReaderImpl<Ability, String>(loader) {

    override val schemaPath = "classpath:data/ability.schema.json"
    override val resourcePath = "classpath:data/5e-SRD-Ability-Scores.json"
    override val resourceType = Array<Ability>::class.java

}