package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

internal class AccountRepositoryTest : DatabaseIntegrationTest() {

    @Autowired
    private lateinit var repository: AccountRepository

    @Test
    fun `should softly delete account`(@RandomParameter account: Account): Unit = runBlocking {
        // given
        account.id = null

        val savedAccount = repository.save(account)
        val accountId = savedAccount.id

        // when
        repository.delete(savedAccount)
        val resultById = repository.findById(accountId!!)
        val deletedAccountsIds = repository.findDeletedIds().toList()

        // then
        assertThat(accountId).isNotNull
        assertThat(resultById).isNull()
        assertThat(deletedAccountsIds).contains(accountId)
    }

    @Test
    fun `should softly delete account by id`(@RandomParameter account: Account): Unit = runBlocking {
        // given
        account.id = null

        val savedAccount = repository.save(account)
        val accountId = savedAccount.id

        // when
        repository.deleteById(accountId!!)
        val resultById = repository.findById(accountId)
        val deletedAccountsIds = repository.findDeletedIds().toList()

        // then
        assertThat(accountId).isNotNull
        assertThat(resultById).isNull()
        assertThat(deletedAccountsIds).contains(accountId)
    }

    @Test
    fun `should find account by username`(@RandomParameter account: Account): Unit = runBlocking {
        // given
        account.id = null

        val savedAccount = repository.save(account)

        // when
        val optionalAccount = repository.findByUsername(savedAccount.username!!)

        // then
        assertThat(optionalAccount?.id)
            .isNotNull
            .isEqualTo(savedAccount.id)
    }

    @Test
    fun `should properly audit entity on save`(@RandomParameter account: Account): Unit = runBlocking {
        // given
        account.id = null

        // when
        val createdAccount = repository.save(account)
        val updatedAccount = repository.save(
            createdAccount.copy(email = "updated email").apply { copyBaseState(createdAccount) }
        )

        // then
        assertThat(createdAccount.created)
            .isNotNull
        assertThat(createdAccount.updated)
            .isNull()

        assertThat(updatedAccount.created)
            .isNotNull
            .isEqualTo(createdAccount.created)
        assertThat(updatedAccount.updated)
            .isNotNull
            .isAfter(updatedAccount.created)
    }

    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun registerProps(registry: DynamicPropertyRegistry) = registerDynamicProps(registry)
    }
}
