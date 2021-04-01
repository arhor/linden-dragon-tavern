package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = AccountDetails.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class AccountDetails(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR)
    @SequenceGenerator(name = SEQ_GENERATOR, sequenceName = SEQ_NAME, allocationSize = SEQ_ALLOC_SIZE)
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null,

    @Column(unique = true, nullable = false, length = 30)
    var username: String? = null,

    @Column(nullable = false, length = 60)
    var firstName: String? = null,

    @Column(nullable = false, length = 60)
    var lastName: String? = null

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = TABLE_NAME

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    var securityProfile: SecurityProfile? = null

    override fun hashCode(): Int {
        return classBasedStaticHashCode()
    }

    companion object {
        const val TABLE_NAME = "account_details"
        private const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        private const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}
