package org.arhor.diploma.service.dto

import org.arhor.diploma.core.MutableIdentity

data class AccountDTO(
    private var id: Long?,
    var username: String?,
    var password: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?
) : MutableIdentity<Long> {

    override fun getId(): Long? {
        return id
    }

    override fun setId(id: Long?) {
        this.id = id
    }
}
