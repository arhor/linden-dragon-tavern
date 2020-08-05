package org.arhor.diploma.service.impl

import org.arhor.diploma.service.dto.Skill
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class SkillService(resourceLoader: ResourceLoader) : DataReaderImpl<Skill, Int>(resourceLoader) {

    override val resourcePath = "classpath:data/5e-SRD-Skills.json"
    override val resourceType = Array<Skill>::class.java

}