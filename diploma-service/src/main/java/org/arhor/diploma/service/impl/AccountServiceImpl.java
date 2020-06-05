package org.arhor.diploma.service.impl;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.service.dto.AccountDTO;
import org.arhor.diploma.data.repository.AccountRepository;
import org.arhor.diploma.service.AccountService;
import org.arhor.commons.util.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final CommonServiceExecutor executor;

  private final AccountRepository repository;
  private final Converter converter;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return repository.findByUsername(username)
        .map(account -> {
          boolean status = !account.isDeleted();
          return new org.springframework.security.core.userdetails.User(
              account.getUsername(),
              account.getPassword(),
              status,
              status,
              status,
              status,
              List.of(new SimpleGrantedAuthority(account.getRole()))
          );
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Override
  public AccountDTO getAccountById(Long id) {
    return executor.getById(repository, AccountDTO.class, id);
  }

  @Override
  public List<AccountDTO> getAccounts(int page, int size) {
    return repository
        .findAll(PageRequest.of(page, size))
        .stream()
        .map(account -> converter.convert(account, AccountDTO.class))
        .collect(toList());
  }
}
