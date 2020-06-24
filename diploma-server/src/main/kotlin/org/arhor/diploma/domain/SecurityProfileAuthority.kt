package org.arhor.diploma.domain

import org.arhor.diploma.util.Common.STATIC_HASH_CODE
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "security_profile_authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class SecurityProfileAuthority {

  @EmbeddedId
  var id: CompositeId? = null

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

  @Embeddable
  class CompositeId(

      @Column(name = "profile_id", nullable = false)
      var profileId: Long?,

      @Column(name = "authority_id", nullable = false)
      var authorityId: Long?

  ) : Serializable {

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is CompositeId) return false

      if (profileId != other.profileId) return false
      if (authorityId != other.authorityId) return false

      return true
    }

    override fun hashCode(): Int {
      var result = profileId?.hashCode() ?: 0
      result = 31 * result + (authorityId?.hashCode() ?: 0)
      return result
    }

    override fun toString(): String {
      return "CompositeId(profileId=$profileId, authorityId=$authorityId)"
    }
  }
}