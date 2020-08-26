package org.arhor.diploma.data.persist.domain

import org.arhor.diploma.data.persist.domain.core.CompositeId
import org.arhor.diploma.util.Common.STATIC_HASH_CODE
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "security_profile_authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SecurityProfileAuthority {

    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "firstId", column = Column(name = "profile_id", nullable = false)),
        AttributeOverride(name = "secondId", column = Column(name = "authority_id", nullable = false))
    )
    var id: CompositeId<Long, Long>? = null

    @ManyToOne(optional = false)
    @MapsId("profile_id")
    var securityProfile: SecurityProfile? = null

    @ManyToOne(optional = false)
    @MapsId("authority_id")
    var authority: Authority? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SecurityProfileAuthority) return false

        if (id != other.id) return false
        if (securityProfile != other.securityProfile) return false
        if (authority != other.authority) return false

        return true
    }

    override fun hashCode(): Int {
        return STATIC_HASH_CODE
    }

    override fun toString(): String {
        return "SecurityProfileAuthority(id=$id, securityProfile=$securityProfile, authority=$authority)"
    }
}