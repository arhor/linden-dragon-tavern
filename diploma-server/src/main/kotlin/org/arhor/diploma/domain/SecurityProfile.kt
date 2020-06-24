package org.arhor.diploma.domain

import org.arhor.diploma.domain.core.AuditableDomainObject
import org.arhor.diploma.domain.core.DomainObject
import org.arhor.diploma.util.Common.STATIC_HASH_CODE
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*


@Entity
@Table(name = "security_profiles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SecurityProfile: AuditableDomainObject<Long>() {

  @Column(unique = true, nullable = false)
  var name: String? = null

  @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
  var accounts: List<Account>? = null

  @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
  var securityAuthorities: List<SecurityProfileAuthority>? = null
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SecurityProfile) return false
    if (!super.equals(other)) return false

    if (name != other.name) return false
    if (accounts != other.accounts) return false
    if (securityAuthorities != other.securityAuthorities) return false

    return true
  }

  override fun hashCode(): Int {
    return STATIC_HASH_CODE
  }

  override fun toString(): String {
    return "SecurityProfile(name=$name, accounts=$accounts, securityAuthorities=$securityAuthorities)"
  }
}