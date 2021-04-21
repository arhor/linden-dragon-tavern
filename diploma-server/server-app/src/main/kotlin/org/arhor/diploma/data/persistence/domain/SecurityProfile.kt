package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(SecurityProfile.TABLE_NAME)
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
        get() = TABLE_NAME

    companion object {
        const val TABLE_NAME = "security_profiles"
        const val CACHE = "${TABLE_NAME}_cache"
        const val CACHE_BY_ID = "${CACHE}_by_id"
        const val CACHE_BY_USERNAME = "${CACHE}_by_username"
    }
}