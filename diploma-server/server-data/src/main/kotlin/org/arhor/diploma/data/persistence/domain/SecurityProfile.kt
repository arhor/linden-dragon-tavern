package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.arhor.diploma.data.STATIC_HASH_CODE
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*


@Entity
@Table(name = "security_profiles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SecurityProfile : AuditableDomainObject<Long>() {

    @Column(unique = true, nullable = false)
    var name: String? = null

    @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _accounts = mutableListOf<Account>()

    val accounts get() = _accounts.toList()

    @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _securityAuthorities = mutableListOf<SecurityProfileAuthority>()

    val securityAuthorities get() = _securityAuthorities.toList()

    fun addAccount(account: Account) {
        _accounts.add(account)
    }

    fun addSecurityProfileAuthority(authority: SecurityProfileAuthority) {
        _securityAuthorities.add(authority)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SecurityProfile) return false
        if (!super.equals(other)) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return STATIC_HASH_CODE
    }

    override fun toString(): String {
        return "SecurityProfile(name=$name)"
    }
}