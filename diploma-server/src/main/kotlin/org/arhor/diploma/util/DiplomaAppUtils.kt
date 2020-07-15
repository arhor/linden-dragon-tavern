package org.arhor.diploma.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication

internal inline fun <reified T> createLogger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

/**
 * Utility to load a Spring profile to be used as default
 * when there is no `spring.profiles.active` set in the environment or as command line argument.
 * If the value is not available in `application.yml` then `dev` profile will be used as default.
 *
 * @param app the Spring application.
 */
internal fun addDefaultProfile(app: SpringApplication) {
    val defProperties: MutableMap<String, Any> = HashMap()
    // The default profile to use when no other profiles are defined
    // This cannot be set in the application.yml file.
    // See https://github.com/spring-projects/spring-boot/issues/1219
    defProperties["spring.profiles.default"] = SpringProfile.DEVELOPMENT
    app.setDefaultProperties(defProperties)
}