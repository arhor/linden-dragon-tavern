package org.arhor.diploma.service.impl;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.domain.Account;
import org.arhor.diploma.service.action.GetEntities;
import org.arhor.diploma.service.action.GetEntity;
import org.arhor.diploma.service.dto.AccountDTO;
import org.arhor.diploma.repository.AccountRepository;
import org.arhor.diploma.service.AccountService;
import org.arhor.diploma.util.Converter;
import org.arhor.diploma.service.exception.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final CommonServiceExecutor executor;

  private final AccountRepository repository;
  private final Converter converter;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return GetEntity.apply(
        username,
        repository::findByUsername,
        this::accountToUserDetails
    ).orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Override
  public AccountDTO getAccountById(Long id) {
    return GetEntity.apply(
        id,
        repository::findById,
        this::accountToDTO
    ).orElseThrow(() -> new EntityNotFoundException("account", "id", id));
  }

  @Override
  public List<AccountDTO> getAccounts(int page, int size) {
    return GetEntities.apply(PageRequest.of(page, size), repository::findAll, this::accountToDTO);
  }

  private AccountDTO accountToDTO(Account a) {
    return converter.convert(a, AccountDTO.class);
  }

  private UserDetails accountToUserDetails(Account a) {
    final boolean status = !a.isDeleted();

    return new org.springframework.security.core.userdetails.User(
        a.getUsername(),
        a.getPassword(),
        status,
        status,
        status,
        status,
        List.of(new SimpleGrantedAuthority(a.getRole()))
    );
  }
}
