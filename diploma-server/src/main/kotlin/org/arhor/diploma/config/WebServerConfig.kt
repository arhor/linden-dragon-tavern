package org.arhor.diploma.config

import org.arhor.diploma.config.properties.CustomProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.server.MimeMappings
import org.springframework.boot.web.server.WebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import javax.servlet.ServletContext
import javax.servlet.ServletException

@Configuration
class WebServerConfig(
    private val env: Environment,
    private val props: CustomProperties
): ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

  @Throws(ServletException::class)
  override fun onStartup(servletContext: ServletContext?) {
    if (env.activeProfiles.isNotEmpty()) {
      log.info("Web application configuration, using profiles: {}", *env.activeProfiles)
    }
    log.info("Web application fully configured")
  }

  /**
   * Customize the Servlet engine: Mime types, the document root, the cache.
   */
  override fun customize(server: WebServerFactory) {
    setMimeMappings(server)
    // When running in an IDE or with ./gradlew bootRun, set location of the static web assets.
    setLocationForStaticAssets(server)
  }

  private fun setMimeMappings(server: WebServerFactory) {
    if (server is ConfigurableServletWebServerFactory) {
      server.setMimeMappings(
          MimeMappings(MimeMappings.DEFAULT).apply {
            val utf8 = StandardCharsets.UTF_8.name().toLowerCase()
            add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + utf8)
            add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + utf8)
          }
      )
    }
  }

  private fun setLocationForStaticAssets(server: WebServerFactory) {
    if (server is ConfigurableServletWebServerFactory) {
      val prefixPath = resolvePathPrefix()
      val root = File(prefixPath + "build/resources/main/static/")
      if (root.exists() && root.isDirectory) {
        server.setDocumentRoot(root)
      }
    }
  }

  /**
   * Resolve path prefix to static resources.
   */
  private fun resolvePathPrefix(): String {
    val path = javaClass.getResource("").path

    val fullExecutablePath: String = try {
      URLDecoder.decode(path, StandardCharsets.UTF_8.name())
    } catch (e: UnsupportedEncodingException) {
      /* try without decoding if this ever happens */
      path
    }

    val rootPath = Paths.get(".").toUri().normalize().path
    val extractedPath = fullExecutablePath.replace(rootPath, "")
    val extractionEndIndex = extractedPath.indexOf("build/")

    return when {
      extractionEndIndex <= 0 -> ""
      else -> extractedPath.substring(0, extractionEndIndex)
    }
  }

  @Bean
  fun corsFilter(): CorsFilter? {
    val source = UrlBasedCorsConfigurationSource()
    val config: CorsConfiguration = props.cors

    config.allowedOrigins?.takeIf { it.isNotEmpty() }?.let {
      log.debug("Registering CORS filter")
      source.registerCorsConfiguration("/api/**", config)
      source.registerCorsConfiguration("/management/**", config)
      source.registerCorsConfiguration("/v2/api-docs", config)
    }

    return CorsFilter(source)
  }

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(WebServerConfig::class.java)
  }
}