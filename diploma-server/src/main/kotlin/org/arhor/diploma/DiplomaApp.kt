package org.arhor.diploma

import org.arhor.diploma.config.properties.ApplicationProperties
import org.arhor.diploma.core.StartupTask
import org.arhor.diploma.service.Reader
import org.arhor.diploma.service.dto.Ability
import org.arhor.diploma.service.dto.Skill
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
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class DiplomaApp(
    private val env: Environment,
    private val startupTasks: List<StartupTask>
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
    fun executeStartupTasks() {
        for (task in startupTasks) {
            task.execute()
        }
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

        log.info(
            """
--------------------------------------------------------------------------------
    Application `${appName}` is running! Access URLs:
    - Local:      ${protocol}://localhost:${serverPort}${contextPath}
    - External:   ${protocol}://${hostAddress}:${serverPort}${contextPath}
    - Profile(s): ${env.activeProfiles.asList()}
--------------------------------------------------------------------------------"""
        )
    }
}
