package org.arhor.diploma.service.dto

import org.arhor.diploma.core.Identifiable

data class AccountDTO(
    override var id: Long?,
    var username: String?,
    var password: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?
) : Identifiable<Long>
