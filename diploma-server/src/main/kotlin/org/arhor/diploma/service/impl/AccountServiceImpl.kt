package org.arhor.diploma.service.impl

import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.mapping.AccountConverter
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.invoke.MethodHandles

@Service
@Transactional
class AccountServiceImpl(
    private val repository: AccountRepository,
    converter: AccountConverter
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

    override fun getAccountById(id: Long) = getOne(id)

    override fun getAccounts(page: Int, size: Int) = getList(page, size)

    override fun createAccount(accountDTO: AccountDTO): Long {
        accountDTO.username
            ?.let { username -> repository.findByUsername(username) }
            ?.ifPresent { account ->
                val message = "Username '${account.username}' is already taken"
                log.error(message)
                throw RuntimeException(message)
            }

        return create(accountDTO).id ?: throw IllegalStateException("Entity ID must be generated!")
    }

    override fun deleteAccount(id: Long) {
        delete(id)
    }

    private fun extractAuthorities(account: Account): Collection<GrantedAuthority> {
        return account.securityProfile
            ?.securityAuthorities
            ?.mapNotNull { profileAuthority -> profileAuthority.authority }
            ?.mapNotNull { authority -> authority.name }
            ?.map { name -> SimpleGrantedAuthority(name) }
            ?: emptyList()
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
