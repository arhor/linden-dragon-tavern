package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.AuthorityRepository
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserOperationsService(
    private val accRepository: AccountRepository,
    private val secRepository: SecurityProfileRepository,
    private val authRepository: AuthorityRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return mono {
            accRepository.findByUsername(username)?.let { account ->
                val securityProfile = secRepository.findByAccountId(account.id!!)
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
            else -> authRepository.findBySecurityProfileId(id!!)
                .mapNotNull { it.name }.toList()
        }.map { SimpleGrantedAuthority(it) }
    }
}