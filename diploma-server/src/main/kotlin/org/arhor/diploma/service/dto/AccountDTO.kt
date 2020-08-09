package org.arhor.diploma.service.dto

import org.arhor.diploma.core.MutableIdentity

data class AccountDTO(
    private var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null
) : MutableIdentity<Long> {

    override fun getId(): Long? {
        return id
    }

    override fun setId(id: Long?) {
        this.id = id
    }
}
