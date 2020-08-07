package org.arhor.diploma.service.impl

import org.arhor.diploma.service.dto.Ability
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AbilityService(resourceLoader: ResourceLoader) : DataReaderImpl<Ability, String>(resourceLoader) {

    override val schemaPath = "classpath:data/ability.schema.json"
    override val resourcePath = "classpath:data/5e-SRD-org.arhor.diploma.core.Ability-Scores.json"
    override val resourceType = Array<Ability>::class.java

}