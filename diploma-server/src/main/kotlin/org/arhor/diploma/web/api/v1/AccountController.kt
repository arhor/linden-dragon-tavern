package org.arhor.diploma.web.api.v1

import org.arhor.diploma.Authorities.Account
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.util.bound
import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/accounts"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AccountController(private val service: AccountService) {

    companion object {
        @JvmStatic
        private val log = createLogger<AccountController>()
    }

    @GetMapping
    @PreAuthorize("hasAuthority('${Account.VIEW}') and hasAuthority('${Account.EDIT}')")
    fun getAccounts(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): List<AccountDTO> {
        return bound<Int, List<AccountDTO>>(service::getAccounts)(page, size)
    }

    @GetMapping(path = ["/{id}"])
    fun getAccount(@PathVariable id: Long): AccountDTO {
        return service.getAccountById(id)
    }
}
