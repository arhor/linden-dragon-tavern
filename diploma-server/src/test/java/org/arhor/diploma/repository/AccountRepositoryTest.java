package org.arhor.diploma.repository;

import org.arhor.diploma.testutils.IntegrationTest;
import org.arhor.diploma.DatabaseIntegrationTest;
import org.arhor.diploma.data.persist.domain.Account;
import org.arhor.diploma.data.persist.repository.AccountRepository;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@IntegrationTest
@ExtendWith({RandomParameter.Resolver.class})
class AccountRepositoryTest extends DatabaseIntegrationTest {

    @Autowired
    private AccountRepository repository;

    @Test
    @Transactional
    void shouldSoftlyDeleteAccount(
            @RandomParameter final String username,
            @RandomParameter final String password,
            @RandomParameter final String firstName,
            @RandomParameter final String lastName,
            @RandomParameter final String email) {

        // given
        var account = new Account();

        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);

        final var savedAccount = repository.save(account);
        final var accountId = savedAccount.getId();

        // when
        repository.delete(savedAccount);

        // then
        assertThat(accountId).isNotNull();
        assertThat(repository.findById(accountId)).isEmpty();
        assertThat(repository.findDeletedIds()).contains(accountId);
    }
}
