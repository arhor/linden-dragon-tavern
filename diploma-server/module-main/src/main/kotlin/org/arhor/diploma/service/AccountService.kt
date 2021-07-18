package org.arhor.diploma.service

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO

interface AccountService : CrudService<Account, AccountDTO, Long> {

    suspend fun getAccountById(id: Long): AccountDTO

    suspend fun getAccounts(page: Int, size: Int): Flow<AccountDTO>

    suspend fun createAccount(accountDTO: AccountDTO): AccountDTO

    suspend fun deleteAccount(id: Long)
}
