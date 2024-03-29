package com.github.arhor.linden.dragon.tavern.infrastructure.startup.tasks

import com.github.arhor.linden.dragon.tavern.common.Priority
import com.github.arhor.linden.dragon.tavern.common.StartupTask
import mu.KotlinLogging
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.UnknownHostException

private val logger = KotlinLogging.logger {}

@Component
class DisplayAppInfo(private val env: Environment) : StartupTask {

    override val priority = Priority.LAST

    private val appName: String?
        get() = env.getProperty("spring.application.name")

    private val serverPort: String?
        get() = env.getProperty("server.port")

    private val contextPath: String
        get() = env.getProperty("server.servlet.context-path")?.takeIf { it.isNotBlank() } ?: "/"

    private val protocol: String
        get() = when (env.getProperty("server.ssl.key-store")) {
            null -> "http"
            else -> "https"
        }

    private val hostAddress: String
        get() = try {
            InetAddress.getLocalHost().hostAddress
        } catch (e: UnknownHostException) {
            val defaultHost = "localhost"
            logger.warn { "The host name could not be determined, using `${defaultHost}` as fallback" }
            defaultHost
        }

    override fun execute() {
        logger.info {
            """
            --------------------------------------------------------------------------------
                Application `${appName}` is running! Access URLs:
                - Local:     ${protocol}://localhost:${serverPort}${contextPath}
                - External:  ${protocol}://${hostAddress}:${serverPort}${contextPath}
                - java ver.: ${System.getProperty("java.version")}
            --------------------------------------------------------------------------------
            """.trimIndent()
        }
    }
}
