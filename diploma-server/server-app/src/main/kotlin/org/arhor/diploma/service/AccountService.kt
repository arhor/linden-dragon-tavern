package org.arhor.diploma.service

import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
import org.springframework.security.core.userdetails.UserDetailsService

interface AccountService : CrudService<Account, AccountDTO, Long>, UserDetailsService {

    fun getAccountById(id: Long): AccountDTO

    fun getAccounts(page: Int, size: Int): List<AccountDTO>

    fun createAccount(accountDTO: AccountDTO): AccountDTO

    fun deleteAccount(id: Long)
}
