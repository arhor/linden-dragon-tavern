package org.arhor.diploma.web.api.v1

import org.arhor.diploma.Authorities.Account
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.web.createPagedModel
import org.slf4j.LoggerFactory
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.lang.invoke.MethodHandles
import javax.validation.Valid
import javax.validation.executable.ValidateOnExecution

@RestController
@RequestMapping(
    path = ["/api/v1/accounts"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@ValidateOnExecution
class AccountController(private val service: AccountService) {

    @GetMapping
    @PreAuthorize("hasAuthority('${Account.VIEW}')")
    fun getAccounts(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
        auth: Authentication?,
    ): PagedModel<*> {
        return service.createPagedModel(page, size, auth, AccountController::getAccounts) { account ->
            account.toRepresentationModel(auth)
        }
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: Long, auth: Authentication?): RepresentationModel<*> {
        return service.getAccountById(id).toRepresentationModel(auth)
    }

    @PostMapping
    fun createAccount(@Valid @RequestBody account: AccountDTO): ResponseEntity<Unit> {
        val createdAccount = service.createAccount(account)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdAccount.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('${Account.EDIT}')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAccount(@PathVariable id: Long, auth: Authentication?) {
        val account = service.getAccountById(id)

        if (selfRequest(account, auth)) {
            service.delete(account)
        } else {
            throw IllegalArgumentException()
        }
    }

    private fun AccountDTO.toRepresentationModel(auth: Authentication?): RepresentationModel<*> {
        return RepresentationModel.of(this)
            .add(linkTo<AccountController> { getAccount(id!!, auth) }.withSelfRel())
    }

    private fun selfRequest(account: AccountDTO, auth: Authentication?): Boolean {
        return account.username == (auth?.principal as UserDetails?)?.username
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
