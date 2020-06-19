package org.arhor.diploma

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration

//@ConfigurationProperties(prefix = "diploma", ignoreUnknownFields = false)
//@PropertySources(
//    PropertySource(value = ["classpath:git.properties"], ignoreResourceNotFound = true),
//    PropertySource(value = ["classpath:META-INF/build-info.properties"], ignoreResourceNotFound = true)
//)
@Component
class CustomProperties {

  val cors: CorsConfiguration = CorsConfiguration()

}