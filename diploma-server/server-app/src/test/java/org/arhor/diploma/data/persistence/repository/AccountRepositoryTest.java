package org.arhor.diploma.data.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.arhor.diploma.data.persistence.domain.Account;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Testable
class AccountRepositoryTest extends DatabaseIntegrationTest {

    @Autowired
    private AccountRepository repository;

    @Test
    @Transactional
    void shouldSoftlyDeleteAccount(@RandomParameter final Account account) {
        // given
        account.setId(null);

        final var savedAccount = repository.save(account);
        final var accountId = savedAccount.getId();

        // when
        repository.delete(savedAccount);

        // then
        assertThat(accountId).isNotNull();
        assertThat(repository.findById(accountId)).isEmpty();
        assertThat(repository.findDeletedIds()).contains(accountId);
    }

    @Test
    @Transactional
    void shouldSoftlyDeleteAccountById(@RandomParameter final Account account) {
        // given
        account.setId(null);

        final var savedAccount = repository.save(account);
        final var accountId = savedAccount.getId();

        // when
        repository.deleteById(accountId);

        // then
        assertThat(accountId).isNotNull();
        assertThat(repository.findById(accountId)).isEmpty();
        assertThat(repository.findDeletedIds()).contains(accountId);
    }

    @Test
    @Transactional
    void shouldFindAccountByUsername(@RandomParameter final Account account) {
        // given
        account.setId(null);

        final var savedAccount = repository.save(account);
        final var savedAccountUsername = savedAccount.getUsername();

        // when
        Optional<Account> optionalAccount = repository.findByUsername(savedAccountUsername);

        // then
        assertThat(optionalAccount).contains(savedAccount);
    }
}
