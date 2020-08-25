package org.arhor.diploma.service.data

import org.arhor.diploma.service.data.model.Monster
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

private typealias MonsterDataProvider = DataProviderImplDefault<Monster, Monster.Details, String>

@Service
class MonsterServiceImpl(loader: ResourceLoader) : MonsterService, MonsterDataProvider(loader) {

    override val resourcePath = "classpath:data/5e-SRD-Monsters.json"
    override val resourceType = Array<Monster.Details>::class.java

    override fun shrinkData(details: Monster.Details): Monster {
        return Monster(
            name = details.name,
            size = details.size,
            type = details.type,
            challengeRating = details.challengeRating
        )
    }
}