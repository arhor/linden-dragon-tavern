package org.arhor.diploma.service;

import org.arhor.diploma.service.dto.AccountDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {

  AccountDTO getAccountById(Long id);

  List<AccountDTO> getAccounts(int page, int size);
}
