package org.arhor.diploma.service.impl

import org.arhor.diploma.domain.Account
import org.arhor.diploma.repository.AccountRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.exception.EntityNotFoundException
import org.arhor.diploma.util.Converter
import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountServiceImpl(
    private val repository: AccountRepository,
    converter: Converter
) : AbstractService<Account, AccountDTO, Long>(converter, repository), AccountService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let {
            repository
                .findByUsername(it)
                .map { account ->
                    val status = !account.isDeleted
                    User(
                        account.username,
                        account.password,
                        status,
                        status,
                        status,
                        status,
                        extractAuthorities(account)
                    )
                }
                .orElseThrow { UsernameNotFoundException(username) }
        } ?: throw IllegalArgumentException(username)
    }

    override fun getAccountById(id: Long) = getOne<AccountDTO>(id)

    override fun getAccounts(page: Int, size: Int) = getList<AccountDTO>(page, size)

    override fun createAccount(accountDTO: AccountDTO): Long {
        accountDTO.username?.let { username -> repository.findByUsername(username) }?.ifPresent { account ->
            val message = "Username '${account.username}' is already taken"
            log.error(message)
            throw RuntimeException(message)
        }

        val savedAccount = accountDTO.convertTo<Account>().let(repository::save)

        return savedAccount.id ?: throw IllegalStateException("Entity ID must be generated!")
    }

    override fun deleteAccount(id: Long) {
        repository.findById(id).orElseThrow {
            EntityNotFoundException(
                className = "Account",
                fieldName = "id",
                fieldValue = id
            )
        }.let(repository::delete)
    }

    private fun extractAuthorities(account: Account): Collection<GrantedAuthority> {
        return account.securityProfile
            ?.securityAuthorities
            ?.map { auth -> auth.authority }
            ?.mapNotNull { auth -> auth?.name }
            ?.map { name -> SimpleGrantedAuthority(name) }
            ?: emptyList()
    }

    companion object {
        @JvmStatic
        private val log: Logger = createLogger<AccountServiceImpl>()
    }
}
