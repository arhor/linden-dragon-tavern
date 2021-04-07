package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = SecurityProfile.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class SecurityProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR)
    @SequenceGenerator(name = SEQ_GENERATOR, sequenceName = SEQ_NAME, allocationSize = SEQ_ALLOC_SIZE)
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null,

    @Column(unique = true, nullable = false)
    var name: String? = null,

    @Column(name = "synthetic", nullable = false)
    var isSynthetic: Boolean = false

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = "security_profiles"

    @OneToMany(
        mappedBy = "securityProfile",
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH],
        orphanRemoval = true
    )
    private val _accounts: MutableList<AccountDetails> = mutableListOf()

    @OneToMany(
        mappedBy = "securityProfile",
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH],
        orphanRemoval = true
    )
    private val _securityAuthorities: MutableList<SecurityProfileAuthority> = mutableListOf()

    val accounts: List<AccountDetails>
        get() = _accounts.toList()

    val securityAuthorities: List<SecurityProfileAuthority>
        get() = _securityAuthorities.toList()

    fun addAccount(account: AccountDetails) {
        _accounts.add(account)
    }

    fun addSecurityProfileAuthority(authority: SecurityProfileAuthority) {
        _securityAuthorities.add(authority)
    }

    override fun hashCode(): Int = classBasedStaticHashCode()

    companion object {
        const val TABLE_NAME = "security_profiles"
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}