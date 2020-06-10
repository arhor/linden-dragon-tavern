package org.arhor.diploma.domain

import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class DomainObject<T : Serializable> : Identifiable<T>, Deletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private var id: T? = null

  @Column(name = "deleted", nullable = false)
  private var isDeleted: Boolean = false

  override fun getId(): T? {
    return id
  }

  override fun setId(id: T?) {
    this.id = id
  }

  override fun isDeleted(): Boolean {
    return isDeleted
  }

  override fun setDeleted(isDeleted: Boolean) {
    this.isDeleted = isDeleted
  }

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
