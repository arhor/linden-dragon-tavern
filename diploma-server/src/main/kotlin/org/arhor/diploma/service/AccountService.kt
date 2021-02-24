package org.arhor.diploma.service

import org.arhor.diploma.service.dto.AccountDTO
import org.springframework.security.core.userdetails.UserDetailsService

interface AccountService : CrudService<AccountDTO, Long>, UserDetailsService {

    fun getAccountById(id: Long): AccountDTO

    fun getAccounts(page: Int, size: Int): List<AccountDTO>

    fun createAccount(accountDTO: AccountDTO): Long

    fun deleteAccount(id: Long)
}
