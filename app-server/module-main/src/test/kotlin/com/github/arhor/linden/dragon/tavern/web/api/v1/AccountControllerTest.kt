package com.github.arhor.linden.dragon.tavern.web.api.v1

import com.github.arhor.linden.dragon.tavern.service.AccountService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@ExtendWith(MockitoExtension::class)
@WebMvcTest(excludeAutoConfiguration = [SecurityAutoConfiguration::class])
@ContextConfiguration(classes = [AccountController::class])
internal class AccountControllerTest {

    @Autowired
    private lateinit var http: MockMvc

    @MockBean
    private lateinit var service: AccountService

//    @Test
//    fun `should return an empty JSON array`() {
//        // given
//        `when`(service.getList(anyInt(), anyInt())).thenReturn(emptyList())
//
//        // when
//        val response = http.get().uri("/api/v1/accounts").exchange()
//
//        // then
//        response
//            .expectStatus().isOk
//            .expectBody().json("[]")
//    }
//
//    @Test
//    fun `should return a JSON array with exactly one item`() {
//        // given
//        val testUsername = "test-username"
//        val testPassword = "test-password"
//
//        `when`(service.getList(anyInt(), anyInt())).thenReturn(
//            listOf(AccountDTO(username = testUsername, password = testPassword))
//        )
//
//        // when
//        val response = http.get().uri("/api/v1/accounts").exchange()
//
//        // then
//        response
//            .expectStatus().isOk
//            .expectBody().json(
//                """
//                [
//                    {
//                        "username": $testUsername,
//                        "password": $testPassword
//                    }
//                ]
//                """.trimIndent()
//            )
//    }
}
