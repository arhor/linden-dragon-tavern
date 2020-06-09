package org.arhor.diploma.web.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.repository.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@EnableCaching
@Configuration
public class CachingConfig {

  @Bean
  public Config hazelcastConfig() {
    return new Config()
        .setInstanceName("hazelcast-instance")
        .addMapConfig(
            new MapConfig()
                .setName(Constants.CACHE_ACCOUNT)
                .setMaxSizeConfig(
                    new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(300));
  }

  /**
   * Scheduled task to clear account caches every 5 minutes.
   */
  @CacheEvict(allEntries = true, value = {Constants.CACHE_ACCOUNT})
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  public void accountCacheEvict() {
    log.info("Flush {} cache(s)", List.of(Constants.CACHE_ACCOUNT));
  }
}
