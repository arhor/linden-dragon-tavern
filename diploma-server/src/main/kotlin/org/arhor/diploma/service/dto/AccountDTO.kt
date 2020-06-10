package org.arhor.diploma.service.dto

import java.time.LocalDateTime

data class AccountDTO(
    var id: Long?,
    var username: String?,
    var password: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?,
    var role: String?,
    var created: LocalDateTime?,
    var updated: LocalDateTime?,
    var deleted: Boolean
)
