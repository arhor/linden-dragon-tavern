package org.arhor.diploma.repository

import org.arhor.diploma.domain.PersistentAuditEvent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PersistentAuditEventRepository : JpaRepository<PersistentAuditEvent, Long> {

  fun findByPrincipal(principal: String): List<PersistentAuditEvent>

  fun findByPrincipalAndAuditEventDateAfterAndAuditEventType(
      principal: String,
      after: LocalDateTime,
      type: String): List<PersistentAuditEvent>

  fun findAllByAuditEventDateBetween(
      fromDate: LocalDateTime,
      toDate: LocalDateTime,
      pageable: Pageable): Page<PersistentAuditEvent>

  fun findByAuditEventDateBefore(before: LocalDateTime): List<PersistentAuditEvent>
}