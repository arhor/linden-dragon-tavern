package org.arhor.diploma.service.dto

import org.arhor.diploma.commons.Identifiable

data class AccountDTO(
    override var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null
) : Identifiable<Long>
