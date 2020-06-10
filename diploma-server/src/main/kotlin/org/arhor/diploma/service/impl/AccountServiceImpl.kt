package org.arhor.diploma.service.impl;

import org.arhor.diploma.domain.Account;
import org.arhor.diploma.service.dto.AccountDTO;
import org.arhor.diploma.repository.AccountRepository;
import org.arhor.diploma.service.AccountService;
import org.arhor.diploma.util.Converter;
import org.arhor.diploma.service.exception.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@Service
@Transactional
class AccountServiceImpl(
    private val repository: AccountRepository,
    private val converter: Converter
) : AccountService {

  override fun loadUserByUsername(username: String?): UserDetails {
    return username?.let {
      repository
          .findByUsername(it)
          .map { account ->
            with(!account.isDeleted()) {
              User(
                  account.username,
                  account.password,
                  this,
                  this,
                  this,
                  this,
                  listOf(SimpleGrantedAuthority(account.role))
              )
            }
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

  private inline fun <T, reified D> convert(item: T): D {
    return converter.convert(item, D::class.java)
  }
}
