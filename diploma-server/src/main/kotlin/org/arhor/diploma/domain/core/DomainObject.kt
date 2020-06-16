package org.arhor.diploma.domain.core

import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class DomainObject<T : Serializable> : Identifiable<T>, Deletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  override var id: T? = null

  @Column(name = "deleted", nullable = false)
  override var isDeleted: Boolean = false

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is DomainObject<*>) return false

    if (id != other.id) return false
    if (isDeleted != other.isDeleted) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + isDeleted.hashCode()
    return result
  }
}
