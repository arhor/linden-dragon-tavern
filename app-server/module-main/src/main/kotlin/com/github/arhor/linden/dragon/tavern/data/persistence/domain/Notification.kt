package com.github.arhor.linden.dragon.tavern.data.persistence.domain

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DomainObject
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(Notification.TABLE_NAME)
data class Notification(
    @Id
    override var id: UUID,

    var type: String,

    var message: String,

    @CreatedDate
    var timestamp: LocalDateTime,

    var accountId: Long

) : DomainObject<UUID>() {

    companion object {
        const val TABLE_NAME = "notifications"
    }
}
