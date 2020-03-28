package org.arhor.diploma.service.impl;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.repository.UserRepository;
import org.arhor.diploma.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

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
}
