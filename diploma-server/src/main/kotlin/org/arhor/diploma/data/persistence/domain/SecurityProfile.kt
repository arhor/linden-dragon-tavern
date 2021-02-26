package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "security_profiles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class SecurityProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${SEQ_GEN_NAME}_security_profiles")
    @SequenceGenerator(
        name = "${SEQ_GEN_NAME}_security_profiles",
        sequenceName = "security_profiles_id_seq",
        allocationSize = SEQ_ALLOC_SIZE,
    )
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
    private val _accounts: MutableList<Account> = mutableListOf()

    @OneToMany(
        mappedBy = "securityProfile",
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH],
        orphanRemoval = true
    )
    private val _securityAuthorities: MutableList<SecurityProfileAuthority> = mutableListOf()

    val accounts: List<Account>
        get() = _accounts.toList()

    val securityAuthorities: List<SecurityProfileAuthority>
        get() = _securityAuthorities.toList()

    fun addAccount(account: Account) {
        _accounts.add(account)
    }

    fun addSecurityProfileAuthority(authority: SecurityProfileAuthority) {
        _securityAuthorities.add(authority)
    }

    override fun hashCode(): Int {
        return classBasedStaticHashCode()
    }
}