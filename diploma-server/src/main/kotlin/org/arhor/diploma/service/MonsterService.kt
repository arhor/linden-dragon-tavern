package org.arhor.diploma.service

import org.arhor.diploma.service.dto.Monster
import org.arhor.diploma.service.dto.MonsterDetails

interface MonsterService : Reader<MonsterDetails, String> {

    fun getMonsterByName(name: String): Monster

    fun getMonsters(): List<Monster>

    fun getMonsters(page: Int, size: Int): List<Monster>
}