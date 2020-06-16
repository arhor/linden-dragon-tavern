package org.arhor.diploma.domain

import org.arhor.diploma.domain.core.AuditableDomainObject
import javax.persistence.*

@Entity
@Table(name = "Account")
class Account : AuditableDomainObject<Long>() {

  @Column(unique = true, nullable = false, length = 30)
  var username: String? = null

  @Column(nullable = false, length = 512)
  var password: String? = null

  @Column(unique = true, length = 128)
  var email: String? = null

  @Column(nullable = false, length = 60)
  var firstName: String? = null

  @Column(nullable = false, length = 60)
  var lastName: String? = null

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  var securityProfile: SecurityProfile? = null

  @Column(length = 10)
  var role: String? = null
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Account) return false
    if (!super.equals(other)) return false

    if (username != other.username) return false
    if (password != other.password) return false
    if (email != other.email) return false
    if (firstName != other.firstName) return false
    if (lastName != other.lastName) return false
    if (role != other.role) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + (username?.hashCode() ?: 0)
    result = 31 * result + (password?.hashCode() ?: 0)
    result = 31 * result + (email?.hashCode() ?: 0)
    result = 31 * result + (firstName?.hashCode() ?: 0)
    result = 31 * result + (lastName?.hashCode() ?: 0)
    result = 31 * result + (role?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "Account(username=$username, password=$password, email=$email, firstName=$firstName, lastName=$lastName, role=$role)"
  }


}
