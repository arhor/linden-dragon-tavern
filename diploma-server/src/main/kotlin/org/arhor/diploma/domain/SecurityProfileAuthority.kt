package org.arhor.diploma.domain

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "security_profile_authorities")
class SecurityProfileAuthority {

  @EmbeddedId
  var id: CompositeId? = null

  @ManyToOne(optional = false)
  @MapsId("profile_id")
  var securityProfile: SecurityProfile? = null

  @ManyToOne(optional = false)
  @MapsId("authority_id")
  var authority: Authority? = null

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