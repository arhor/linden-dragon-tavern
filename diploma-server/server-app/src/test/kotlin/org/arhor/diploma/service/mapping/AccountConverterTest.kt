package org.arhor.diploma.service.mapping

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig
@ExtendWith(RandomParameter.Resolver::class)
internal class AccountConverterTest {

    @TestConfiguration
    @ComponentScan("org.arhor.diploma.service.mapping")
    internal class TestConfig

    @Autowired
    lateinit var mapper: Converter<Account, AccountDTO>

    @Test
    fun `should convert all fields correctly`(
        @RandomParameter account: Account
    ) {
        // when
        val dto = mapper.mapEntityToDto(account)

        // then
        assertThat(dto).isNotNull

        assertSoftly { softly ->
            softly.assertThat(dto.id).`as`("id").isEqualTo(account.id)
            softly.assertThat(dto.username).`as`("username").isEqualTo(account.username)
            softly.assertThat(dto.password).`as`("password").isEqualTo(account.password)
            softly.assertThat(dto.email).`as`("email").isEqualTo(account.email)
        }
    }
}