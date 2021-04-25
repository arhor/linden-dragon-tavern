package org.arhor.diploma.data.persistence.repository;

import kotlinx.coroutines.runBlocking
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Testable
class AccountRepositoryTest : DatabaseIntegrationTest() {

    @Autowired
    private lateinit var repository: AccountRepository

//    @Test
//    @Transactional
//    void shouldSoftlyDeleteAccount(@RandomParameter final Account account) {
//        // given
//        account.setId(null);
//
//        final var savedAccount = repository.save(account);
//        final var accountId = savedAccount.getId();
//
//        // when
//        repository.delete(savedAccount);
//
//        // then
//        assertThat(accountId).isNotNull();
//        assertThat(repository.findById(accountId)).isEmpty();
//        assertThat(repository.findDeletedIds()).contains(accountId);
//    }

//    @Test
//    @Transactional
//    void shouldSoftlyDeleteAccountById(@RandomParameter final Account account) {
//        // given
//        account.setId(null);
//
//        final var savedAccount = repository.save(account);
//        final var accountId = savedAccount.getId();
//
//        // when
//        repository.deleteById(accountId);
//
//        // then
//        assertThat(accountId).isNotNull();
//        assertThat(repository.findById(accountId)).isEmpty();
//        assertThat(repository.findDeletedIds()).contains(accountId);
//    }

    @Test
    @Transactional
    fun shouldFindAccountByUsername(@RandomParameter account: Account): Unit = runBlocking {
        // given
        val savedAccount = repository.save(account.apply { id = null })

        // when
        val optionalAccount = repository.findByUsername(savedAccount.username!!)

        // then
        assertThat(optionalAccount)
            .isNotNull
            .isEqualTo(savedAccount)
    }
}
