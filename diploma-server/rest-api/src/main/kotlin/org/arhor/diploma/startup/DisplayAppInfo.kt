package org.arhor.diploma.startup

import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.StartupTask
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles
import java.net.InetAddress
import java.net.UnknownHostException

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
            log.warn("The host name could not be determined, using `localhost` as fallback")
            "localhost"
        }

    override fun execute() {
        log.info(
            """
--------------------------------------------------------------------------------
    Application `${appName}` is running! Access URLs:
    - Local:     ${protocol}://localhost:${serverPort}${contextPath}
    - External:  ${protocol}://${hostAddress}:${serverPort}${contextPath}
    - java ver.: ${System.getProperty("java.version")}
--------------------------------------------------------------------------------"""
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}