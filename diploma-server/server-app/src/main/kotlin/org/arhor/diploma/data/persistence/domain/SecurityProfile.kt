package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("security_profiles")
data class SecurityProfile(

    @Id
    @Column("id")
    override var id: Long?,

    @Column("name")
    var name: String?,

    @Column("synthetic")
    var isSynthetic: Boolean

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = "security_profiles"

    companion object {
        const val TABLE_NAME = "security_profiles"
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}