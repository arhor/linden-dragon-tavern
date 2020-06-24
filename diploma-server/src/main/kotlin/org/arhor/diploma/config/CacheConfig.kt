package org.arhor.diploma.config

import com.hazelcast.config.*
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.spring.cache.HazelcastCacheManager
import net.bytebuddy.utility.RandomString
import org.arhor.diploma.config.properties.CustomProperties
import org.arhor.diploma.util.Cache
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.util.SpringProfile.HAZELCAST
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.scheduling.annotation.Scheduled
import java.io.Serializable
import java.lang.reflect.Method
import java.time.format.DateTimeFormatter
import javax.annotation.PreDestroy

@EnableCaching
@Configuration
class CacheConfig(private val env: Environment) {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(CacheConfig::class.java)
  }

  @Autowired(required = false)
  private var gitProperties: GitProperties? = null

  @Autowired(required = false)
  private var buildProperties: BuildProperties? = null

  @PreDestroy
  fun destroy() {
    if (env.activeProfiles.contains(HAZELCAST)) {
      log.info("Closing Cache Manager")
      Hazelcast.shutdownAll()
    }
  }

  @Bean
  @Profile(HAZELCAST)
  fun cacheManager(hazelcastInstance: HazelcastInstance): CacheManager {
    log.debug("Starting HazelcastCacheManager")
    return HazelcastCacheManager(hazelcastInstance)
  }

  @Bean
  @Profile(HAZELCAST)
  fun hazelcastInstance(props: CustomProperties): HazelcastInstance {
    log.debug("Configuring Hazelcast")

    Hazelcast.getHazelcastInstanceByName("diploma-server")?.let {
      log.debug("Hazelcast already initialized")
      return@hazelcastInstance it
    }

    val config = Config()
    config.instanceName = "diploma-server"
    config.networkConfig.port = 5701
    config.networkConfig.isPortAutoIncrement = true

    // In development, remove multicast auto-configuration
    if (env.acceptsProfiles(Profiles.of(SpringProfile.DEVELOPMENT))) {
      System.setProperty("hazelcast.local.localAddress", "127.0.0.1")
      config.networkConfig.join.awsConfig.isEnabled = false
      config.networkConfig.join.multicastConfig.isEnabled = false
      config.networkConfig.join.tcpIpConfig.isEnabled = false
    }
    config.mapConfigs["default"] = initializeDefaultMapConfig(props)

    // Full reference is available at:
    // https://docs.hazelcast.org/docs/management-center/3.9/manual/html/Deploying_and_Starting.html
    config.managementCenterConfig = initializeDefaultManagementCenterConfig(props)
    config.mapConfigs["org.arhor.diploma.domain.*"] = initializeDomainMapConfig(props)
    return Hazelcast.newHazelcastInstance(config)
  }

  @Bean
  fun keyGenerator(): KeyGenerator {
    data class PrefixedSimpleKey(
        val prefix: String,
        val methodName: String,
        val elements: List<Any>
    ) : Serializable {

      private val hashCode: Int by lazy(LazyThreadSafetyMode.NONE) {
        var result = prefix.hashCode()
        result = 31 * result + methodName.hashCode()
        result = 31 * result + elements.hashCode()
        result
      }

      override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PrefixedSimpleKey) return false

        if (prefix != other.prefix) return false
        if (methodName != other.methodName) return false
        if (elements != other.elements) return false

        return true
      }

      override fun hashCode(): Int {
        return hashCode
      }
    }

    return object : KeyGenerator {

      private val prefix: String by lazy(LazyThreadSafetyMode.NONE) {
        gitProperties?.shortCommitId
            ?: buildProperties?.time?.let { DateTimeFormatter.ISO_INSTANT.format(it) }
            ?: buildProperties?.version
            ?: RandomString.make(12)

      }

      override fun generate(target: Any, method: Method, vararg params: Any): Any {
        return PrefixedSimpleKey(
            prefix,
            method.name,
            listOf(params)
        )
      }
    }
  }

  /**
   * Scheduled task to clear account caches every 5 minutes.
   */
  @CacheEvict(allEntries = true, value = [Cache.Names.ACCOUNT])
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  fun accountCacheEvict() {
    log.info("Flush {} cache(s)", listOf(Cache.Names.ACCOUNT))
  }

  private fun initializeDomainMapConfig(props: CustomProperties): MapConfig? {
    return MapConfig().setTimeToLiveSeconds(props.timeToLiveSeconds)
  }

  private fun initializeDefaultMapConfig(props: CustomProperties): MapConfig {
    return MapConfig().apply {
      // Number of backups. If 1 is set as the backup-count for example,
      // then all entries of the map will be copied to another JVM for
      // fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
      backupCount = props.backupCount

      // Valid values are:
      // NONE (no eviction),
      // LRU (Least Recently Used),
      // LFU (Least Frequently Used).
      // NONE is the default.
      evictionPolicy = EvictionPolicy.LRU

      // Maximum size of the map. When max size is reached,
      // map is evicted based on the policy defined.
      // Any integer between 0 and Integer.MAX_VALUE. 0 means
      // Integer.MAX_VALUE. Default is 0.
      maxSizeConfig = MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE)
    }
  }

  private fun initializeDefaultManagementCenterConfig(props: CustomProperties): ManagementCenterConfig {
    return ManagementCenterConfig().apply {
      isEnabled = props.isManagementCenterEnabled
      url = props.managementCenterUrl
      updateInterval = props.managementCenterUpdateInterval
    }
  }
}
