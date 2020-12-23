package org.arhor.diploma.web.api.v1

import org.arhor.diploma.Authorities.Account
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.util.bound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping(
    path = ["/api/v1/accounts"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AccountController(private val service: AccountService) {

    @GetMapping
    @PreAuthorize("hasAuthority('${Account.VIEW}')")
    fun getAccounts(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): List<AccountDTO> {
        return bound<Int, List<AccountDTO>>(service::getAccounts)(page, size)
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: Long): AccountDTO {
        return service.getAccountById(id)
    }

    @PutMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('${Account.EDIT}')")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateAccount(@RequestBody dto: AccountDTO, auth: Authentication): ResponseEntity<Unit> {


        if (dto.username == (auth.principal as UserDetails).username) {

        } else {
            throw IllegalArgumentException()
        }

        val newAccountId = service.createAccount(dto)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newAccountId)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
