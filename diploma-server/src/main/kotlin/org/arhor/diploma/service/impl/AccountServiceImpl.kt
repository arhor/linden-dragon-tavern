package org.arhor.diploma.service.impl;

import org.arhor.diploma.domain.Account
import org.arhor.diploma.repository.AccountRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.exception.EntityNotFoundException
import org.arhor.diploma.util.Converter
import org.springframework.data.domain.PageRequest
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
                account.extractAuthorities()
            )
          }
          .orElseThrow { UsernameNotFoundException(username) }
    } ?: throw IllegalArgumentException(username)
  }

  override fun getAccountById(id: Long): AccountDTO {
    return repository
        .findById(id)
        .map<AccountDTO> { convert(it) }
        .orElseThrow { EntityNotFoundException("account", "id", id) }
  }

  override fun getAccounts(page: Int, size: Int): List<AccountDTO> {
    return repository
        .findAll(PageRequest.of(page, size))
        .map<AccountDTO> { convert(it) }
        .toList()
  }
}

private fun Account.extractAuthorities(): Collection<GrantedAuthority> {
  return securityProfile
      ?.securityAuthorities
      ?.map { it.authority }
      ?.mapNotNull { it?.name }
      ?.map { SimpleGrantedAuthority(it) }
      ?: emptyList()
}