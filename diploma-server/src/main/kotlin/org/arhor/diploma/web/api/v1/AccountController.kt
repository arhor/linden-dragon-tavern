package org.arhor.diploma.web.api.v1;

import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.util.*
import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/accounts"],
    produces = [MediaType.APPLICATION_JSON_VALUE])
class AccountController(private val service: AccountService) {

  @GetMapping
  @PreAuthorize("hasAuthority('${ACCOUNT_VIEW}') and hasAuthority('${ACCOUNT_EDIT}')")
  fun getAccounts(
      @RequestParam(required = false) page: Int?,
      @RequestParam(required = false) size: Int?
  ): List<AccountDTO> {
    return service.getAccounts(page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE)
  }

  @GetMapping(path = ["/{id}"])
  fun getAccount(@PathVariable id: Long): AccountDTO {
    return service.getAccountById(id)
  }

  companion object {
    @JvmStatic
    private val log: Logger = createLogger<AccountController>()
  }
}
