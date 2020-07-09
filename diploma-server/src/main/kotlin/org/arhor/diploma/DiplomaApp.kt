package org.arhor.diploma

import org.arhor.diploma.config.properties.ApplicationProperties
import org.arhor.diploma.startup.StartupVerifier
import org.arhor.diploma.util.Failure
import org.arhor.diploma.util.Success
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

  companion object {
    @JvmStatic
    private val log: Logger = createLogger<DiplomaApp>()

    @JvmStatic
    fun main(args: Array<String>) {
      runApplication<DiplomaApp>(*args)
    }
  }

  @PostConstruct
  fun verifyStartup() {
    log.info("Starting app verification")
    log.info("Found [${verifiers.size}] verifiers to run")

    val width = DecimalFormat("0".repeat(verifiers.size.toString().length))

    val success = verifiers.sorted().mapIndexed { i, verifier ->
      val result = verifier.verify()
      when (result) {
        is Success -> log.info("${width.format(i)}: ${result.value}")
        is Failure -> log.error("${width.format(i)}: ${result.error.message}")
      }
      result
    }.all { it.isSuccess }

    if (!success) {
      log.error("An error occurred during startup verification")
      exitProcess(0)
    }

    log.info("App verification finished successfully")
  }

  @Bean
  fun run() = CommandLineRunner {
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
}
