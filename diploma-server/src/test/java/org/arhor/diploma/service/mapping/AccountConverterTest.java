package org.arhor.diploma.service.mapping;

import org.arhor.diploma.commons.Converter;
import org.arhor.diploma.data.persist.domain.Account;
import org.arhor.diploma.service.dto.AccountDTO;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringJUnitConfig
@ExtendWith({RandomParameter.Resolver.class})
class AccountConverterTest {

    @TestConfiguration
    @ComponentScan("org.arhor.diploma.service.mapping")
    static class TestConfig {}

    @Autowired
    private Converter<Account, AccountDTO> mapper;

    @Test
    void shouldConvertAllFieldsCorrectly(@RandomParameter final Account testAccount) {
        // given
        var account = testAccount;

        // when
        var dto = mapper.entityToDto(account);

        // then
        assertThat(dto).isNotNull();

        assertSoftly(softly -> {
            softly.assertThat(dto.getId()).as("id").isEqualTo(account.getId());
            softly.assertThat(dto.getUsername()).as("username").isEqualTo(account.getUsername());
            softly.assertThat(dto.getPassword()).as("password").isEqualTo(account.getPassword());
            softly.assertThat(dto.getFirstName()).as("firstName").isEqualTo(account.getFirstName());
            softly.assertThat(dto.getLastName()).as("lastName").isEqualTo(account.getLastName());
            softly.assertThat(dto.getEmail()).as("email").isEqualTo(account.getEmail());
        });
    }
}
