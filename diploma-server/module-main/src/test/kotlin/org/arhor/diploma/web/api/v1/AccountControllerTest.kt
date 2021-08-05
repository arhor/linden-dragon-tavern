package org.arhor.diploma.web.api.v1

import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(MockitoExtension::class)
@WebFluxTest(excludeAutoConfiguration = [ReactiveSecurityAutoConfiguration::class])
@ContextConfiguration(classes = [AccountController::class])
internal class AccountControllerTest {

    @Autowired
    private lateinit var http: WebTestClient

    @MockBean
    private lateinit var service: AccountService

    @Test
    fun `should return an empty JSON array`() {
        // given
        `when`(service.getList(anyInt(), anyInt())).thenReturn(emptyFlow())

        // when
        val response = http.get().uri("/api/v1/accounts").exchange()

        // then
        response
            .expectStatus().isOk
            .expectBody().json("[]")
    }

    @Test
    fun `should return a JSON array with exactly one item`() {
        // given
        val testUsername = "test-username"
        val testPassword = "test-password"

        `when`(service.getList(anyInt(), anyInt())).thenReturn(
            flow {
                emit(AccountDTO(username = testUsername, password = testPassword))
            }
        )

        // when
        val response = http.get().uri("/api/v1/accounts").exchange()

        // then
        response
            .expectStatus().isOk
            .expectBody().json(
                """
                [
                    {
                        "username": $testUsername,
                        "password": $testPassword
                    }
                ]
                """.trimIndent()
            )
    }
}