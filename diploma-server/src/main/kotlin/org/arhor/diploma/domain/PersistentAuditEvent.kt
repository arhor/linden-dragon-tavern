package org.arhor.diploma.domain

import org.arhor.diploma.util.Common.SERIAL_VERSION
import org.arhor.diploma.util.Common.STATIC_HASH_CODE
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "persistent_audit_events")
class PersistentAuditEvent : Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  var id: Long? = null

  @Column(nullable = false)
  var principal: String? = null

  @Column(name = "event_date")
  var auditEventDate: Instant? = null

  @Column(name = "event_type")
  var auditEventType: String? = null

  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(name = "persistent_audit_event_data", joinColumns = [JoinColumn(name = "event_id")])
  var data: Map<String, String>? = null

  companion object {
    private const val serialVersionUID = SERIAL_VERSION
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is PersistentAuditEvent) return false

    if (id != other.id) return false
    if (principal != other.principal) return false
    if (auditEventDate != other.auditEventDate) return false
    if (auditEventType != other.auditEventType) return false
    if (data != other.data) return false

    return true
  }

  override fun hashCode(): Int {
    return STATIC_HASH_CODE
  }

  override fun toString(): String {
    return "PersistentAuditEvent(" +
        "id=$id, " +
        "principal=$principal, " +
        "auditEventDate=$auditEventDate, " +
        "auditEventType=$auditEventType, " +
        "data=$data" +
        ")"
  }
}