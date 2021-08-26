package org.arhor.diploma.dnd.data.repository

import org.arhor.diploma.dnd.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@Tag("integration-test")
@SpringJUnitConfig(TestConfig::class)
class MonsterReaderTest {

    @Autowired
    private lateinit var service: MonsterRepository

    @Test
    fun fetchAllMonsters() {
        // when
        val monsters = service.getPage()

        // then
        assertThat(monsters)
            .isNotNull

        assertThat(monsters.items)
            .isNotNull
            .isNotEmpty
    }
}
