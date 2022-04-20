package com.github.arhor.linden.dragon.tavern.service.mapping

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Account
import com.github.arhor.linden.dragon.tavern.service.dto.AccountDTO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig
@ExtendWith(com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
internal class AccountConverterTest {

    @TestConfiguration
    @ComponentScan("com.github.arhor.linden.dragon.tavern.service.mapping")
    internal class TestConfig

    @Autowired
    lateinit var mapper: Converter<Account, AccountDTO>

    @Test
    fun `should convert all fields correctly`(
        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter account: Account
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
