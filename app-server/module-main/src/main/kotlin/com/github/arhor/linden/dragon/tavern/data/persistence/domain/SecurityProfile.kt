package com.github.arhor.linden.dragon.tavern.data.persistence.domain

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(SecurityProfile.TABLE_NAME)
data class SecurityProfile(

    @Id
    override var id: Long? = null,

    var name: String? = null,

    @Column("synthetic")
    var isSynthetic: Boolean = false

) : AuditableDomainObject<Long>() {

    companion object {
        const val TABLE_NAME = "security_profiles"
        const val CACHE = "${TABLE_NAME}_cache"
        const val CACHE_BY_ID = "${CACHE}_by_id"
        const val CACHE_BY_USERNAME = "${CACHE}_by_username"
    }
}
