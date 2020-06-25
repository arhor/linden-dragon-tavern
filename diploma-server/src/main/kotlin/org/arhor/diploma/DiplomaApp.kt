package org.arhor.diploma

import org.arhor.diploma.config.properties.ApplicationProperties
import org.arhor.diploma.util.Failure
import org.arhor.diploma.startup.StartupVerifier
import org.arhor.diploma.util.Success
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.DecimalFormat
import javax.annotation.PostConstruct
import kotlin.system.exitProcess

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class DiplomaApp(
    private val env: Environment,
    private val verifiers: List<StartupVerifier>
) {

  /**
   * Initializes DiplomaApp.
   *
   * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
   */
  @PostConstruct
  fun initApplication() {
    val isDev = env.activeProfiles.contains(SpringProfile.DEVELOPMENT)

    if (isDev && env.activeProfiles.contains(SpringProfile.PRODUCTION)) {
      log.error(MISCONFIGURED_MSG, SpringProfile.DEVELOPMENT, SpringProfile.PRODUCTION)
    }
    if (isDev && env.activeProfiles.contains(SpringProfile.CLOUD)) {
      log.error(MISCONFIGURED_MSG, SpringProfile.DEVELOPMENT, SpringProfile.CLOUD)
    }
  }

  @Bean
  fun run() = CommandLineRunner {
    verifyStartUp()
    logSuccessfulStartup()
  }

  private fun verifyStartUp() {
    val verifiersCount = verifiers.size

    if (verifiersCount <= 0) {
      return
    }

    log.info("Starting app verification")
    log.info("Found [${verifiersCount}] verifiers to run")

    val width = DecimalFormat("0".repeat(verifiersCount.toString().length))

    var startupFailed = false

    for (i in verifiers.indices) {
      when (val result = verifiers[i].verify()) {
        is Success -> log.info("${width.format(i)}: ${result.message}")
        is Failure -> {
          log.error(result.message)
          startupFailed = true
        }
      }
    }

    if (startupFailed) {
      exitProcess(0)
    }
  }

  private fun logSuccessfulStartup() {
    val appName = env.getProperty("spring.application.name")
    val serverPort = env.getProperty("server.port")
    val contextPath = env.getProperty("server.servlet.context-path")?.takeIf { it.isNotBlank() } ?: "/"

    val protocol = when (env.getProperty("server.ssl.key-store")) {
      null -> "http"
      else -> "https"
    }

    val hostAddress = try {
      InetAddress.getLocalHost().hostAddress
    } catch (e: UnknownHostException) {
      log.warn("The host name could not be determined, using `localhost` as fallback")
      "localhost"
    }

    log.info("""
--------------------------------------------------------------------------------
  Application `${appName}` is running! Access URLs:
  - Local:      ${protocol}://localhost:${serverPort}${contextPath}
  - External:   ${protocol}://${hostAddress}:${serverPort}${contextPath}
  - Profile(s): ${env.activeProfiles.asList()}
--------------------------------------------------------------------------------"""
    )
  }

  companion object {
    const val MISCONFIGURED_MSG = "Profiles '{}' and '{}' should not run both at the same time!"

    @JvmStatic
    private val log: Logger = createLogger<DiplomaApp>()

    @JvmStatic
    fun main(args: Array<String>) {
      runApplication<DiplomaApp>(*args)
    }
  }
}
