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
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}
