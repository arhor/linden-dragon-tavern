package org.arhor.diploma.web.api.v1

import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.arhor.diploma.service.AccountService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

private val logger = KotlinLogging.logger {}

@Component
class AccountHandler(private val service: AccountService) {

    suspend fun getAccounts(request: ServerRequest): ServerResponse {
        val page = request.queryParamOrNull("page")?.toInt()
        val size = request.queryParamOrNull("size")?.toInt()

        logger.debug { "fetching accounts page: $page, size: $size" }

        val result = service.getList(page ?: 0, size ?: 0).toList()

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getAccount(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()

        logger.debug { "fetching account with id: $id" }

        val result = service.getAccountById(id)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

//    @PostMapping
//    suspend fun createAccount(@Valid @RequestBody account: AccountDTO): ResponseEntity<Unit> {
//
//        logger.debug("creating an account")
//
//        val createdAccount = service.createAccount(account)
//
//        val location = ServletUriComponentsBuilder.fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(createdAccount.id)
//            .toUri()
//
//        return ResponseEntity.created(location).build()
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("isAuthenticated() and hasAuthority('${Account.EDIT}')")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    suspend fun deleteAccount(@PathVariable id: Long, auth: Authentication?) {
//        val account = service.getAccountById(id)
//
//        if (selfRequest(account, auth)) {
//            service.delete(account)
//        } else {
//            throw IllegalArgumentException()
//        }
//    }
//
//    private suspend fun AccountDTO.toRepresentationModel(auth: Authentication?): RepresentationModel<*> {
//        return RepresentationModel.of(this)
//            .add(linkTo<AccountHandler> { getAccount(id!!, auth) }.withSelfRel())
//    }
//
//    private fun selfRequest(account: AccountDTO, auth: Authentication?): Boolean {
//        return account.username == (auth?.principal as UserDetails?)?.username
//    }
}
