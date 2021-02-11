package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.CompositeId2
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "security_profile_authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class SecurityProfileAuthority(
    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "value1", column = Column(name = "profile_id", nullable = false)),
        AttributeOverride(name = "value2", column = Column(name = "authority_id", nullable = false))
    )
    var id: CompositeId2<Long, Long>? = null,
) {

    @ManyToOne(optional = false)
    @MapsId("profile_id")
    var securityProfile: SecurityProfile? = null

    @ManyToOne(optional = false)
    @MapsId("authority_id")
    var authority: Authority? = null

    override fun hashCode(): Int {
        return classBasedStaticHashCode()
    }
}