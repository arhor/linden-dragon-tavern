package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${DomainObject.SEQ_GEN_NAME}_accounts")
    @SequenceGenerator(
        name = "${DomainObject.SEQ_GEN_NAME}_accounts",
        sequenceName = "accounts_id_seq",
        allocationSize = DomainObject.SEQ_ALLOC_SIZE
    )
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null,

    @Column(unique = true, nullable = false, length = 30)
    var username: String? = null,

    @Column(nullable = false, length = 512)
    var password: String? = null,

    @Column(unique = true, length = 128)
    var email: String? = null,

    @Column(nullable = false, length = 60)
    var firstName: String? = null,

    @Column(nullable = false, length = 60)
    var lastName: String? = null,
) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = "accounts"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    var securityProfile: SecurityProfile? = null

    override fun hashCode(): Int {
        return classBasedStaticHashCode()
    }
}
