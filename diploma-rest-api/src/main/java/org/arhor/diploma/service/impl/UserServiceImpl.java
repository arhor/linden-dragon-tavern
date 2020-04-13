package org.arhor.diploma.service.impl;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.dto.UserDTO;
import org.arhor.diploma.exception.EntityNotFoundException;
import org.arhor.diploma.repository.UserRepository;
import org.arhor.diploma.service.UserService;
import org.arhor.diploma.util.Converter;
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
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final Converter converter;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return repository.findByUsername(username)
        .map(user -> {
          boolean status = !user.isDeleted();
          return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              status,
              status,
              status,
              status,
              List.of(new SimpleGrantedAuthority(user.getRole()))
          );
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Override
  public UserDTO getUserById(Long id) {
    return repository
        .findById(id)
        .map(account -> converter.convert(account, UserDTO.class))
        .orElseThrow(() -> new EntityNotFoundException("User", "id", id));
  }

  @Override
  public List<UserDTO> getUsers(int page, int size) {
    return repository
        .findAll(PageRequest.of(page, size)).stream()
        .map(account -> converter.convert(account, UserDTO.class))
        .collect(toList());
  }
}
