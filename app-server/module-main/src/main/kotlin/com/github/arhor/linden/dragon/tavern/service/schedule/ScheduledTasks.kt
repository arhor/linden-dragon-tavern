package com.github.arhor.linden.dragon.tavern.service.schedule;

import com.github.arhor.linden.dragon.tavern.common.time.MINUTE
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Account
import mu.KLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledTasks {

    /**
     * Scheduled task to clear account caches every 5 minutes.
     */
    @CacheEvict(allEntries = true, value = [Account.CACHE])
    @Scheduled(fixedDelay = 5 * MINUTE, initialDelay = 5 * MINUTE)
    fun accountCacheEvict() {
        logger.debug { "Flush [${Account.CACHE}] cache(s)" }
    }

    companion object : KLogging()
}
