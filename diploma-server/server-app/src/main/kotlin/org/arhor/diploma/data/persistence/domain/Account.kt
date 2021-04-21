package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(Account.TABLE_NAME)
data class Account(

    @Id
    @Column("id")
    override var id: Long? = null,

    @Column("username")
    var username: String? = null,

    @Column("password")
    var password: String? = null,

    @Column("email")
    var email: String? = null

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = TABLE_NAME

    companion object {
        const val TABLE_NAME = "accounts"
        const val CACHE = "${TABLE_NAME}_cache"
        const val CACHE_BY_ID = "${CACHE}_by_id"
        const val CACHE_BY_USERNAME = "${CACHE}_by_username"
    }
}
