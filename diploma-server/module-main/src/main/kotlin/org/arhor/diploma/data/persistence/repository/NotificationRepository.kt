package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Notification
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NotificationRepository : CoroutineCrudRepository<Notification, UUID> {

    @Query(
        """
        SELECT n.*
        FROM ${Notification.TABLE_NAME} n
        WHERE n.account_id IN (:accountIds)
        FOR UPDATE SKIP LOCKED
        """
    )
    fun findAllByAccountIdsWithLock(accountIds: Iterable<Long>): Flow<Notification>
}