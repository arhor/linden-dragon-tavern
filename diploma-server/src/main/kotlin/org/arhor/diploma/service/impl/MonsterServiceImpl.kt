package org.arhor.diploma.service.impl

import org.arhor.diploma.service.MonsterService
import org.arhor.diploma.service.dto.Monster
import org.arhor.diploma.service.dto.MonsterDetails
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class MonsterServiceImpl(
    resourceLoader: ResourceLoader
) : MonsterService, DataReaderImpl<MonsterDetails, String>(resourceLoader) {

    override val resourcePath = "classpath:data/5e-SRD-Monsters.json"
    override val resourceType = Array<MonsterDetails>::class.java

    override fun getMonsterByName(name: String): Monster {
        return getOne(name).let(::detailsToMonster)
    }

    override fun getMonsters(): List<Monster> {
        return getList().map(::detailsToMonster)
    }

    override fun getMonsters(page: Int, size: Int): List<Monster> {
        return getList(page, size).map(::detailsToMonster)
    }

    private fun detailsToMonster(details: MonsterDetails): Monster {
        return Monster(
            name = details.name,
            size = details.size,
            type = details.type,
            challengeRating = details.challengeRating
        )
    }
}