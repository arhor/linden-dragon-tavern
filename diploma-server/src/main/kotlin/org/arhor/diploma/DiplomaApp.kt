package org.arhor.diploma

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import java.net.InetAddress
import java.net.UnknownHostException
import javax.annotation.PostConstruct

@SpringBootApplication
class DiplomaApp(private val env: Environment) {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(DiplomaApp::class.java)

    @JvmStatic
    fun logApplicationStartup(env: Environment) {
      var protocol = "http"
      if (env.getProperty("server.ssl.key-store") != null) {
        protocol = "https"
      }
      val serverPort = env.getProperty("server.port")
      var contextPath = env.getProperty("server.servlet.context-path") ?: ""

      if (contextPath.isBlank()) {
        contextPath = "/"
      }
      var hostAddress: String? = "localhost"
      try {
        hostAddress = InetAddress.getLocalHost().hostAddress
      } catch (e: UnknownHostException) {
        log.warn("The host name could not be determined, using `localhost` as fallback")
      }
      log.info("""
----------------------------------------------------------
  Application ${env.getProperty("spring.application.name")} is running! Access URLs:
  Local: 		  ${protocol}://localhost:${serverPort}${contextPath}
  External: 	${protocol}://${hostAddress}:${serverPort}${contextPath}
  Profile(s): ${env.activeProfiles}
----------------------------------------------------------""")
    }
  }

  /**
   * Initializes DiplomaApp.
   *
   * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
   */
  @PostConstruct
  fun initApplication() {
    val isDev = env.activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT)

    if (isDev && env.activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
      log.error(wrongConfigMessage(SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_PRODUCTION))
    }
    if (isDev && env.activeProfiles.contains(SPRING_PROFILE_CLOUD)) {
      log.error(wrongConfigMessage(SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_CLOUD))
    }
  }

  @Bean
  fun run() = CommandLineRunner {
    for (arg in it) {
      println(arg)
    }
  }

  private fun wrongConfigMessage(profile1: String, profile2: String) = """
You have misconfigured your application!
It should not run with both the '$profile1' and '$profile2' profiles at the same time.
"""
}

fun main(args: Array<String>) {
//  val app = SpringApplication(DiplomaApp::class.java)
//  DefaultProfileUtil.addDefaultProfile(app)
//  val env: Environment = app.run(*args).environment
//  DiplomaApp.logApplicationStartup(env)
  runApplication<DiplomaApp>(*args)
}