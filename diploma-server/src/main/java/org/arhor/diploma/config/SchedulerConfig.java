package org.arhor.diploma.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import static org.arhor.diploma.Constants.CACHE_USERS;

@Slf4j
@Configuration
public class SchedulerConfig {

  /** Scheduled task to clear cache `users` every 5 minutes. */
  @CacheEvict(allEntries = true, value = { CACHE_USERS })
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  public void reportUsersCacheEvict() {
    log.info("Flush `users` cache");
  }

//  /** Scheduled task to clear caches `langs` and `roles` every 24 hours. */
//  @CacheEvict(allEntries = true, value = { CACHE_LANGS, CACHE_ROLES })
//  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 500)
//  public void reportLangsRolesCacheEvict() {
//    log.info("Flush `langs` and `roles` caches");
//  }
}
