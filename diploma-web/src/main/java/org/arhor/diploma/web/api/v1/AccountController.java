package org.arhor.diploma.web.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.service.dto.AccountDTO;
import org.arhor.diploma.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.arhor.diploma.web.util.PageUtils.bound;

@Slf4j
@RestController
@RequestMapping(
    path = "/api/v1/users",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

  private final AccountService service;

  @GetMapping
  public List<AccountDTO> getAccounts(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(service::getAccounts).apply(page, size);
  }

  @GetMapping(path = "/{id}")
  public Long getAccount(@PathVariable Long id) {
    return id;
  }
}
