package com.github.arhor.linden.dragon.tavern.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.pagination.DEFAULT_PAGE
import com.github.arhor.linden.dragon.tavern.common.pagination.DEFAULT_SIZE
import com.github.arhor.linden.dragon.tavern.service.AccountService
import com.github.arhor.linden.dragon.tavern.service.dto.AccountDTO
import mu.KLogging
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val service: AccountService) {

    @GetMapping
    fun getAccounts(
        @RequestParam(defaultValue = DEFAULT_PAGE) page: Int,
        @RequestParam(defaultValue = DEFAULT_SIZE) size: Int,
    ): List<AccountDTO> {
        logger.debug { "fetching accounts page: $page, size: $size" }
        return service.getList(page, size)
    }

    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: Long): AccountDTO {
        logger.debug { "fetching account with id: $accountId" }
        return service.getOne(accountId)
    }

    @PostMapping
    fun createAccount(
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
    fun deleteAccount(@PathVariable accountId: Long, auth: Authentication?) {
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

    companion object : KLogging()
}
