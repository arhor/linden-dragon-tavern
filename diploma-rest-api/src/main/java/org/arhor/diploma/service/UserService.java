package org.arhor.diploma.service;

import org.arhor.diploma.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  UserDTO getUserById(Long id);

  List<UserDTO> getUsers(int page, int size);
}
