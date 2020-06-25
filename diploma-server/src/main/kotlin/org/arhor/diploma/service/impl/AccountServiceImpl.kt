package org.arhor.diploma.service.impl;

import org.arhor.diploma.domain.Account
import org.arhor.diploma.repository.AccountRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.util.Converter
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
                account.springAuthorities
            )
          }
          .orElseThrow { UsernameNotFoundException(username) }
    } ?: throw IllegalArgumentException(username)
  }

  override fun getAccountById(id: Long) = getOne<AccountDTO>(id)

  override fun getAccounts(page: Int, size: Int) = getList<AccountDTO>(page, size)
}

private inline val Account.springAuthorities: Collection<GrantedAuthority>
  get() {
    return securityProfile
      ?.securityAuthorities
      ?.map { auth -> auth.authority }
      ?.mapNotNull { auth -> auth?.name }
      ?.map { name -> SimpleGrantedAuthority(name) }
      ?: emptyList()
  }