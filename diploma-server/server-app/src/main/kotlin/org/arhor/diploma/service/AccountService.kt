package org.arhor.diploma.service

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService
//import org.springframework.security.core.userdetails.UserDetailsService

interface AccountService : CrudService<Account, AccountDTO, Long> /*, ReactiveUserDetailsService*/ {

    suspend fun getAccountById(id: Long): AccountDTO

    suspend fun getAccounts(page: Int, size: Int): Flow<AccountDTO>

    suspend fun createAccount(accountDTO: AccountDTO): AccountDTO

    suspend fun deleteAccount(id: Long)
}
