package com.github.arhor.linden.dragon.tavern.data.persistence.domain

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(Account.TABLE_NAME)
data class Account(

    @Id
    override var id: Long? = null,

    var username: String? = null,

    var password: String? = null,

    var email: String? = null,

    var firstName: String? = null,

    var lastName: String? = null,

    var profileId: Long? = null

) : AuditableDomainObject<Long>() {

    companion object {
        const val TABLE_NAME = "accounts"
        const val CACHE = "${TABLE_NAME}_cache"
        const val CACHE_BY_ID = "${CACHE}_by_id"
        const val CACHE_BY_USERNAME = "${CACHE}_by_username"
    }
}
