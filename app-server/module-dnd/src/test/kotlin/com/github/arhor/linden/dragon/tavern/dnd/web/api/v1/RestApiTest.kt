//package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1
//
//import com.github.arhor.linden.dragon.tavern.dnd.data.repository.MonsterRepository
//import com.github.arhor.linden.dragon.tavern.dnd.data.repository.SpellRepository
//import com.github.arhor.linden.dragon.tavern.dnd.service.CharsheetService
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.web.reactive.server.WebTestClient
//
//@ExtendWith(MockitoExtension::class)
//@WebFluxTest(excludeAutoConfiguration = [ReactiveSecurityAutoConfiguration::class])
//@ContextConfiguration(
//    classes = [
//        CharacterSheetController::class,
//        MonsterController::class,
//        SpellController::class,
//    ]
//)
//internal abstract class RestApiTest {
//
//    @Autowired
//    protected lateinit var http: WebTestClient
//
//    @MockBean
//    protected lateinit var service: CharsheetService
//
//    @MockBean
//    protected lateinit var monsterProvider: MonsterRepository
//
//    @MockBean
//    protected lateinit var spellProvider: SpellRepository
//}
