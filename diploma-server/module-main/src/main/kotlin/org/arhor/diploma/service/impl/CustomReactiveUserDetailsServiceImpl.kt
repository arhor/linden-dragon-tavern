package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.AuthorityRepository
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.springframework.context.annotation.Profile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@Profile("production")
class CustomReactiveUserDetailsServiceImpl(
    private val accountRepository: AccountRepository,
    private val authorityRepository: AuthorityRepository,
    private val securityProfileRepository: SecurityProfileRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return mono {
            accountRepository.findByUsername(username)?.let { account ->
                val securityProfile = securityProfileRepository.findByAccountId(account.id!!)
                val status = !account.isDeleted
                User(
                    account.username,
                    account.password,
                    status,
                    status,
                    status,
                    status,
                    securityProfile.authorities()
                )
            } ?: throw UsernameNotFoundException(username)
        }
    }

    private suspend fun SecurityProfile?.authorities(): List<GrantedAuthority> {
        if (this == null) {
            return emptyList()
        }
        return when {
            isSynthetic -> listOf("ROLE_${name}")
            else -> authorityRepository.findBySecurityProfileId(id!!)
                .mapNotNull { it.name }
                .toList()
        }.map { SimpleGrantedAuthority(it) }
    }
}