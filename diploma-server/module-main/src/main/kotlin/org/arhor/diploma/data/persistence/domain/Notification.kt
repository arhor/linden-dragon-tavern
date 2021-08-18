package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table(Notification.TABLE_NAME)
data class Notification(
    @Id
    override val id: UUID,
    val type: String,
    val message: String,
    val status: Status,
    val timestamp: LocalDateTime,
    val accountId: Long
) : DomainObject<UUID>() {

    enum class Status { NEW, PENDING, SENT }

    companion object {
        const val TABLE_NAME = "notifications"
    }
}