package org.arhor.diploma.config.properties

import org.arhor.diploma.util.Cache
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration

//@ConfigurationProperties(prefix = "diploma", ignoreUnknownFields = false)
//@PropertySources(
//    PropertySource(value = ["classpath:git.properties"], ignoreResourceNotFound = true),
//    PropertySource(value = ["classpath:META-INF/build-info.properties"], ignoreResourceNotFound = true)
//)
@Component
class CustomProperties {

  val cors: CorsConfiguration = CorsConfiguration()

//  @Value("cache.hazelcast.timeToLiveSeconds")
  var timeToLiveSeconds: Int = Cache.Hazelcast.TIME_TO_LIVE_SECONDS

//  @Value("cache.hazelcast.backupCount")
  var backupCount: Int = Cache.Hazelcast.BACKUP_COUNT

//  @Value("cache.hazelcast.managementCenter.enabled")
  var isManagementCenterEnabled: Boolean = Cache.Hazelcast.ManagementCenter.ENABLED

//  @Value("cache.hazelcast.managementCenter.url")
  var managementCenterUrl: String = Cache.Hazelcast.ManagementCenter.URL

//  @Value("cache.hazelcast.managementCenter.updateInterval")
  var managementCenterUpdateInterval: Int = Cache.Hazelcast.ManagementCenter.UPDATE_INTERVAL

}