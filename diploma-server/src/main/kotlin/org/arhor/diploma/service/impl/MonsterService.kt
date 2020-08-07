package org.arhor.diploma.service.impl

import org.arhor.diploma.service.dto.Monster
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class MonsterService(resourceLoader: ResourceLoader) : DataReaderImpl<Monster, String>(resourceLoader) {

    override val resourcePath = "classpath:data/5e-SRD-Monsters.json"
    override val resourceType = Array<Monster>::class.java

}