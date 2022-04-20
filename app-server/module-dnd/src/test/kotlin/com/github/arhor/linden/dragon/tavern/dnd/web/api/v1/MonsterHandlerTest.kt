//package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1
//
//import com.github.arhor.linden.dragon.tavern.dnd.data.model.Monster
//import com.github.arhor.linden.dragon.tavern.testutils.RandomParameter
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.verify
//import org.springframework.test.web.reactive.server.expectBody
//
//@ExtendWith(RandomParameter.Resolver::class)
//internal class MonsterHandlerTest : RestApiTest() {
//
//    @Test
//    fun `should return an empty JSON array and call getList method without args`() {
//        // when
//        val response = http.get().uri("/api/v1/monsters").exchange()
//
//        // then
//        response
//            .expectStatus().isOk
//            .expectBody().json("""{ "items": [], "total": 0 }""")
//
//        verify(monsterProvider).getPage()
//    }
//
//    @Test
//    fun `should return an empty JSON array and call getList method with page and size args`() {
//        // given
//        val page = 5
//        val size = 5
//
//        // when
//        val response = http.get().uri("/api/v1/monsters?page=${page}&size=${size}").exchange()
//
//        // then
//        response
//            .expectStatus().isOk
//            .expectBody().json("""{ "items": [], "total": 0 }""")
//
//        verify(monsterProvider).getPage(page, size)
//    }
//
//    @Test
//    fun `should return an expected monster object`() {
//        // given
//        val expectedMonster =
//            Monster(name = "Ancient Red Dragon", size = "large", type = "dragon", challengeRating = 20.0)
//
//        `when`(monsterProvider.findById(expectedMonster.name!!)).thenReturn(expectedMonster)
//
//        // when
//        val response = http.get().uri("/api/v1/monsters/${expectedMonster.name}").exchange()
//
//        // then
//        response
//            .expectStatus().isOk
//            .expectBody<Monster>().isEqualTo(expectedMonster)
//    }
//}
