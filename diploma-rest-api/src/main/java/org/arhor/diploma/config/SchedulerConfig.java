package org.arhor.diploma.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static org.arhor.diploma.Constants.CACHE_ACCOUNT_BY_ID;
import static org.arhor.diploma.Constants.CACHE_ACCOUNT_BY_USERNAME;

@Slf4j
@Configuration
public class SchedulerConfig {

  /**
   * Scheduled task to clear account caches every 5 minutes.
   */
  @CacheEvict(allEntries = true, value = {CACHE_ACCOUNT_BY_ID, CACHE_ACCOUNT_BY_USERNAME})
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  public void reportUsersCacheEvict() {
    log.info("Flush {} cache(s)", List.of(CACHE_ACCOUNT_BY_ID, CACHE_ACCOUNT_BY_USERNAME));
  }
}
