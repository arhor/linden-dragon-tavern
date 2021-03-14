package org.arhor.diploma.config

import org.arhor.diploma.data.Cache
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import java.lang.invoke.MethodHandles

@EnableCaching
@Configuration
class CacheConfig {

    /**
     * Scheduled task to clear account caches every 5 minutes.
     */
    @CacheEvict(allEntries = true, value = [Cache.Names.ACCOUNT])
    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
    fun accountCacheEvict() {
        log.info("Flush {} cache(s)", listOf(Cache.Names.ACCOUNT))
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
