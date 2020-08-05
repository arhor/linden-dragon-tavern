package org.arhor.diploma.service.dto

data class AccountDTO(
    override var id: Long?,
    var username: String?,
    var password: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?
) : DTO<Long>
