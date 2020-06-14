package org.arhor.diploma.config

import com.hazelcast.config.Config
import com.hazelcast.config.EvictionPolicy
import com.hazelcast.config.MapConfig
import com.hazelcast.config.MaxSizeConfig
import org.arhor.diploma.repository.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled

@EnableCaching
@Configuration
class CachingConfig {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(CachingConfig::class.java)
  }

  @Bean
  @Profile("production")
  fun hazelcastConfig(): Config {
    return Config()
        .setInstanceName("hazelcast-instance")
        .addMapConfig(
            MapConfig()
                .setName(Constants.CACHE_ACCOUNT)
                .setMaxSizeConfig(MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(300)
        )
  }

  /**
   * Scheduled task to clear account caches every 5 minutes.
   */
  @CacheEvict(allEntries = true, value = [Constants.CACHE_ACCOUNT])
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  fun accountCacheEvict() {
    log.info("Flush {} cache(s)", listOf(Constants.CACHE_ACCOUNT))
  }
}
