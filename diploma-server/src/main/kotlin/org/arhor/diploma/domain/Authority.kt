package org.arhor.diploma.domain

import org.arhor.diploma.domain.core.DomainObject
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "authorities")
class Authority: DomainObject<Long>() {

  @Column(unique = true, nullable = false)
  var name: String? = null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Authority) return false
    if (!super.equals(other)) return false
    if (name != other.name) return false
    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + (name?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "Authority(name=$name)"
  }
}