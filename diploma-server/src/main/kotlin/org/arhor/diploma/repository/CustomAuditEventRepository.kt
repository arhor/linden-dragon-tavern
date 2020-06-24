package org.arhor.diploma.repository

import org.arhor.diploma.audit.AuditEventConverter
import org.arhor.diploma.domain.PersistentAuditEvent
import org.arhor.diploma.util.Common.ANONYMOUS_USER
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Repository
class CustomAuditEventRepository(
    private val auditEventRepository: PersistentAuditEventRepository,
    private val auditEventConverter: AuditEventConverter
) : AuditEventRepository {

  override fun find(principal: String, after: Instant, type: String): List<AuditEvent> {
    val auditEvents =
        auditEventRepository.findByPrincipalAndAuditEventDateAfterAndAuditEventType(
            principal,
            LocalDateTime.ofInstant(after, ZoneOffset.UTC),
            type
        )
    return auditEventConverter.convertToAuditEvent(auditEvents)
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  override fun add(event: AuditEvent) {
    if (AUTHORIZATION_FAILURE != event.type && ANONYMOUS_USER != event.principal) {
      auditEventRepository.save(
          PersistentAuditEvent().apply {
            principal = event.principal
            auditEventType = event.type
            auditEventDate = event.timestamp
            data = truncate(auditEventConverter.convertDataToStrings(event.data))
          }
      )
    }
  }

  private fun truncate(data: Map<String, String>?): Map<String, String> {
    return data?.map { (key, value) ->
      key to if (value.length > EVENT_DATA_MAX_LENGTH) {
        log.warn(
            "Event data for $key too long (${value.length}) has been truncated to $EVENT_DATA_MAX_LENGTH. " +
            "Consider increasing column width."
        )
        value.substring(0, EVENT_DATA_MAX_LENGTH)
      } else {
        value
      }
    }?.toMap() ?: emptyMap()
  }

  companion object {
    @JvmStatic
    private val log = LoggerFactory.getLogger(CustomAuditEventRepository::class.java)

    private const val AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE"
    private const val EVENT_DATA_MAX_LENGTH = 255 // Should be the same as in Liquibase migration.
  }
}