package com.github.arhor.linden.dragon.tavern.data.persistence.repository

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Notification
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface NotificationRepository : CrudRepository<Notification, UUID> {

    @Query(
        """
        SELECT n.*
        FROM ${Notification.TABLE_NAME} n
        WHERE n.account_id IN (:accountIds)
        FOR UPDATE SKIP LOCKED
        """
    )
    fun findAllByAccountIdsWithLock(accountIds: Iterable<Long>): List<Notification>
}
