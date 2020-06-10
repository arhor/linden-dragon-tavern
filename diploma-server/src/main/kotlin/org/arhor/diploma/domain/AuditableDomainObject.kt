package org.arhor.diploma.domain;

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class AuditableDomainObject<T : Serializable> : DomainObject<T>(), Auditable {

  @Column
  private var created: LocalDateTime? = null

  @Column
  private var updated: LocalDateTime? = null

  @PrePersist
  override fun onCreate() {
    setCreated(LocalDateTime.now())
  }

  @PreUpdate
  override fun onUpdate() {
    setUpdated(LocalDateTime.now())
  }

  override fun getCreated(): LocalDateTime? {
    return created
  }

  override fun setCreated(created: LocalDateTime?) {
    this.created = created
  }

  override fun getUpdated(): LocalDateTime? {
    return updated
  }

  override fun setUpdated(updated: LocalDateTime?) {
    this.updated = updated
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is AuditableDomainObject<*>) return false
    if (!super.equals(other)) return false

    if (created != other.created) return false
    if (updated != other.updated) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + (created?.hashCode() ?: 0)
    result = 31 * result + (updated?.hashCode() ?: 0)
    return result
  }
}
