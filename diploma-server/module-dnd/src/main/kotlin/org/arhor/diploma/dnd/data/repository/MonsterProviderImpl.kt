package org.arhor.diploma.dnd.data.repository

import org.arhor.diploma.dnd.data.model.Monster
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

private typealias MonsterDataProvider = DataProviderImplDefault<Monster, Monster.Details, String>

@Service
class MonsterProviderImpl(loader: ResourceLoader) : MonsterProvider, MonsterDataProvider(loader) {

    override val resourceName get() = "monster"
    override val resourcePath get() = "classpath:dnd/data/5e-SRD-Monsters.json"
    override val resourceType get() = Array<Monster.Details>::class.java

    override fun shrinkData(details: Monster.Details): Monster {
        return Monster(
            name = details.name,
            size = details.size,
            type = details.type,
            challengeRating = details.challengeRating
        )
    }
}