package org.arhor.diploma.util

import org.springframework.boot.SpringApplication

/**
 * Utility to load a Spring profile to be used as default
 * when there is no `spring.profiles.active` set in the environment or as command line argument.
 * If the value is not available in `application.yml` then `dev` profile will be used as default.
 */
private const val SPRING_PROFILE_DEFAULT = "spring.profiles.default"

/**
 * Set a default to use when no profile is configured.
 *
 * @param app the Spring application.
 */
internal fun addDefaultProfile(app: SpringApplication) {
  val defProperties: MutableMap<String, Any> = HashMap()
  // The default profile to use when no other profiles are defined
  // This cannot be set in the application.yml file.
  // See https://github.com/spring-projects/spring-boot/issues/1219
  defProperties[SPRING_PROFILE_DEFAULT] = SpringProfile.DEVELOPMENT
  app.setDefaultProperties(defProperties)
}