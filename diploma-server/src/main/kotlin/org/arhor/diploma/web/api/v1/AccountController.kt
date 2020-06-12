package org.arhor.diploma.web.api.v1;

import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.util.PageUtils.bound
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/users"],
    produces = [MediaType.APPLICATION_JSON_VALUE])
class AccountController(private val service: AccountService) {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(AccountController::class.java)
  }

  @GetMapping
  fun getAccounts(
      @RequestParam(required = false) page: Int,
      @RequestParam(required = false) size: Int
  ): List<AccountDTO> {
    return bound<Int, List<AccountDTO>>(service::getAccounts).apply(page, size);
  }

  @GetMapping(path = ["/{id}"])
  fun getAccount(@PathVariable id: Long): AccountDTO {
    return service.getAccountById(id)
  }
}
