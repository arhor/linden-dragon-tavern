package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(AccountDetails.TABLE_NAME)
data class AccountDetails(

    @Id
    @Column("id")
    override var id: Long? = null,

    @Column("username")
    var username: String? = null,

    @Column("firstName")
    var firstName: String? = null,

    @Column("lastName")
    var lastName: String? = null

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = TABLE_NAME

    override fun hashCode(): Int = classBasedStaticHashCode()

    companion object {
        const val TABLE_NAME = "account_details"
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}
