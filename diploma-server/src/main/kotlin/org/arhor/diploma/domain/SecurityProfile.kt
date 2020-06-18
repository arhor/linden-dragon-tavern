package org.arhor.diploma.domain

import org.arhor.diploma.domain.core.AuditableDomainObject
import org.arhor.diploma.domain.core.DomainObject
import javax.persistence.*


@Entity
@Table(name = "security_profiles")
class SecurityProfile: AuditableDomainObject<Long>() {

  @Column(unique = true, nullable = false)
  var name: String? = null

  @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
  var accounts: List<Account>? = null

  @OneToMany(mappedBy = "securityProfile", cascade = [CascadeType.ALL], orphanRemoval = true)
  var securityAuthorities: List<SecurityProfileAuthority>? = null

}