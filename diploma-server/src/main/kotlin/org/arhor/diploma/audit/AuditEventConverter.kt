package org.arhor.diploma.audit

import org.arhor.diploma.domain.PersistentAuditEvent
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class AuditEventConverter {

  /**
   * Convert a list of [PersistentAuditEvent]s to a list of [AuditEvent]s.
   *
   * @param auditEvents the list to convert.
   * @return the converted list.
   */
  fun convertToAuditEvent(auditEvents: Iterable<PersistentAuditEvent>?): List<AuditEvent> {
    return auditEvents?.map { convertToAuditEvent(it) } ?: emptyList()
  }

  /**
   * Internal conversion. This method will allow to save additional data.
   * By default, it will save the object as string.
   *
   * @param data the data to convert.
   * @return a map of [String], [String].
   */
  fun convertDataToStrings(data: Map<String, Any>?): Map<String, String> {
    return data?.let {
      val results = HashMap<String, String>(it.size)

      for ((key, value) in it) {
        if (value is WebAuthenticationDetails) {
          results["remoteAddress"] = value.remoteAddress
          results["sessionId"] = value.sessionId
        } else {
          results[key] = Objects.toString(value)
        }
      }

      results
    } ?: emptyMap()
  }

  /**
   * Convert a [PersistentAuditEvent] to an [AuditEvent].
   *
   * @param event the event to convert.
   * @return the converted list.
   */
  private fun convertToAuditEvent(event: PersistentAuditEvent): AuditEvent {
    return AuditEvent(
        event.auditEventDate,
        event.principal,
        event.auditEventType,
        convertDataToObjects(event.data)
    )
  }

  /**
   * Internal conversion. This is needed to support the current SpringBoot actuator `AuditEventRepository` interface.
   *
   * @param data the data to convert.
   * @return a map of [String], [Object].
   */
  private fun convertDataToObjects(data: Map<String, String>?): Map<String, Any> {
    return data?.map { (key, value) -> key to value }?.toMap() ?: emptyMap()
  }
}