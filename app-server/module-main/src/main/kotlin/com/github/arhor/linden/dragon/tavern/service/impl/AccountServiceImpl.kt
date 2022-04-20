package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.Role.USER
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.SecurityProfile
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.AccountRepository
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.AuthorityRepository
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.SecurityProfileRepository
import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
import com.github.arhor.linden.dragon.tavern.service.AccountService
import com.github.arhor.linden.dragon.tavern.service.dto.AccountDTO
import com.github.arhor.linden.dragon.tavern.service.mapping.AccountConverter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val authorityRepository: AuthorityRepository,
    private val securityProfileRepository: SecurityProfileRepository,
    private val converter: AccountConverter,
) : AccountService, UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository.findByUsername(username)?.let { account ->
            val securityProfile = securityProfileRepository.findByAccountId(account.id!!)
            val accountStatus = account.isDeleted

            User.builder()
                .username(account.username)
                .password(account.password)
                .disabled(accountStatus)
                .accountExpired(accountStatus)
                .credentialsExpired(accountStatus)
                .accountLocked(accountStatus)
                .authorities(securityProfile.authorities())
                .build()
        } ?: throw UsernameNotFoundException(username)
    }

    @Transactional
    override fun create(item: AccountDTO): AccountDTO {
        item.username?.takeIf { accountRepository.existsByUsername(it) }?.let {
            throw RuntimeException("Username '$it' is already taken")
        }

        val (userProfileId, _, _) = securityProfileRepository.findByRole(USER)
            ?: throw EntityNotFoundException("SecurityProfile", "role = $USER")

        val createdAccount = converter.mapDtoToEntity(item)
            .apply { profileId = userProfileId }
            .let { accountRepository.save(it) }

        return converter.mapEntityToDto(createdAccount)
    }

    override fun getOne(id: Long): AccountDTO {
        return accountRepository.findById(id)
            .map { converter.mapEntityToDto(it) }
            .orElseThrow { EntityNotFoundException("Account", "id = $id") }
    }

    override fun getList(): List<AccountDTO> {
        return accountRepository.findAll().map(converter::mapEntityToDto)
    }

    override fun getList(page: Int, size: Int): List<AccountDTO> {
        return accountRepository.findAll().drop(page * size).take(size).map(converter::mapEntityToDto)
    }

    override fun getTotalSize(): Long {
        return accountRepository.count()
    }

    override fun update(item: AccountDTO): AccountDTO {
        if (item.id != null) {
            val id = item.id!!
            if (accountRepository.existsById(id)) {
                return converter.mapDtoToEntity(item)
                    .let { accountRepository.save(it) }
                    .let(converter::mapEntityToDto)
            } else {
                throw EntityNotFoundException("Account", "id = $id")
            }
        } else {
            throw IllegalArgumentException("Entity id must not be null")
        }
    }

    override fun delete(id: Long) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id)
        } else {
            throw EntityNotFoundException("Account", "id = $id")
        }
    }

    override fun delete(item: AccountDTO) {
        item.id?.let { delete(it) }
    }

    private fun SecurityProfile?.authorities(): List<GrantedAuthority> {
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
