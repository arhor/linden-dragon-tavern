package org.arhor.diploma.web.api.v1

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.arhor.diploma.commons.pagination.DEFAULT_PAGE
import org.arhor.diploma.commons.pagination.DEFAULT_SIZE
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val service: AccountService) {

    @GetMapping
    fun getAccounts(
        @RequestParam(defaultValue = DEFAULT_PAGE) page: Int,
        @RequestParam(defaultValue = DEFAULT_SIZE) size: Int,
    ): Flow<AccountDTO> {
        logger.debug { "fetching accounts page: $page, size: $size" }
        return service.getList(page, size)
    }

    @GetMapping("/{accountId}")
    suspend fun getAccount(@PathVariable accountId: Long): AccountDTO {
        logger.debug { "fetching account with id: $accountId" }
        return service.getOne(accountId)
    }

    @PostMapping
    suspend fun createAccount(
        @Valid @RequestBody account: AccountDTO,
        request: HttpRequest
    ): ResponseEntity<AccountDTO> {
        logger.debug("creating an account")

        val createdAccount = service.create(account)

        val location = UriComponentsBuilder.fromHttpRequest(request)
            .path("/{id}")
            .buildAndExpand(createdAccount.id)
            .toUri()

        return ResponseEntity.created(location).body(createdAccount)
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("isAuthenticated() and hasAuthority('ACCOUNT:EDIT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteAccount(@PathVariable accountId: Long, auth: Authentication?) {
        val account = service.getOne(accountId)

        if (selfRequest(account, auth)) {
            service.delete(account)
        } else {
            throw IllegalArgumentException()// TODO: replace with more appropriate exception type
        }
    }

    private fun selfRequest(account: AccountDTO, auth: Authentication?): Boolean {
        return account.username == (auth?.principal as UserDetails?)?.username
    }
}
