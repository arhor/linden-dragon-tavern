package org.arhor.diploma.service.impl

import mu.KLogging
import org.arhor.diploma.Roles
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.mapping.AccountConverter
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
    private val accRepository: AccountRepository,
    private val secRepository: SecurityProfileRepository,
    converter: AccountConverter
) : AbstractService<Account, AccountDTO, Long>(converter, accRepository), AccountService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let {
            accRepository
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

    @Transactional
    override fun createAccount(accountDTO: AccountDTO): AccountDTO {
        accountDTO.username
            ?.let { username -> accRepository.findByUsername(username) }
            ?.ifPresent { account ->
                throw RuntimeException("Username '${account.username}' is already taken")
            }

        val userSecurityProfile = secRepository.findByNameOrNull(Roles.USER.name)
            ?: throw RuntimeException("Missing security profile: 'USER'")

        return create(accountDTO) {
            accountDetails?.securityProfile = userSecurityProfile
        }
    }

    override fun deleteAccount(id: Long) {
        delete(id)
    }

    private fun extractAuthorities(account: Account): Collection<GrantedAuthority> {
        val securityProfile = account.accountDetails?.securityProfile

        val authorities = when {
            securityProfile == null -> {
                emptyList()
            }
            securityProfile.isSynthetic -> {
                listOf("ROLE_${securityProfile.name}")
            }
            else -> {
                securityProfile.securityAuthorities
                    .mapNotNull { it.authority }
                    .mapNotNull { it.name }
            }
        }

        return authorities.map { SimpleGrantedAuthority(it) }
    }

    companion object : KLogging()
}
