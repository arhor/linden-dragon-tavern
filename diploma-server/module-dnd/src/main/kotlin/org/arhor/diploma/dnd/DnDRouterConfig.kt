package org.arhor.diploma.dnd

import org.arhor.diploma.dnd.web.api.v1.CharacterSheetController
import org.arhor.diploma.dnd.web.api.v1.MonsterController
import org.arhor.diploma.dnd.web.api.v1.SpellController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class DnDRouterConfig {

    @Bean
    fun dndModuleRouter(
        characterSheetController: CharacterSheetController,
        monsterController: MonsterController,
        spellController: SpellController
    ) = coRouter {
        // @formatter:off
        "/api/v1".nest {

            GET("/charsheets", characterSheetController::downloadCharsheet)

            "/monsters".nest {
                GET(""               , monsterController::getMonsterList)
                GET("/details"       , monsterController::getMonsterDetailsList)
                GET("/{name}"        , monsterController::getMonster)
                GET("/{name}/details", monsterController::getMonsterDetails)
            }

            "/spells".nest {
                GET(""               , spellController::getSpellList)
                GET("/{name}"        , spellController::getSpell)
                GET("/{name}/details", spellController::getSpellDetails)

                POST("reload"        , spellController::reloadDataProvider)
            }
        }
        // @formatter:on
    }
}