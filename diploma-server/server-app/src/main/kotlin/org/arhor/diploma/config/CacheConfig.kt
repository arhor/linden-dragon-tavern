package org.arhor.diploma.config

import mu.KotlinLogging
import org.arhor.diploma.commons.time.MINUTE
import org.arhor.diploma.commons.time.SECOND
import org.arhor.diploma.data.persistence.domain.Account
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

private val logger = KotlinLogging.logger {}

@EnableCaching
@Configuration
class CacheConfig {

    /**
     * Scheduled task to clear account caches every 5 minutes.
     */
    @CacheEvict(allEntries = true, value = [Account.CACHE])
    @Scheduled(fixedDelay = 5 * MINUTE, initialDelay = SECOND)
    fun accountCacheEvict() {
        logger.info { "Flush [${Account.CACHE}] cache(s)" }
    }
}
