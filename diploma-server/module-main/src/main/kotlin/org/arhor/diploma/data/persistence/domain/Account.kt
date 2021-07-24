package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(Account.TABLE_NAME)
data class Account(

    @Id
    override var id: Long? = null,

    var username: String? = null,

    var password: String? = null,

    var email: String? = null

) : AuditableDomainObject<Long>() {

    companion object {
        const val TABLE_NAME = "accounts"
        const val CACHE = "${TABLE_NAME}_cache"
        const val CACHE_BY_ID = "${CACHE}_by_id"
        const val CACHE_BY_USERNAME = "${CACHE}_by_username"
    }
}