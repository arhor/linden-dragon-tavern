package org.arhor.diploma.config.properties

import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration

@Component
class CustomProperties {

    val cors: CorsConfiguration = CorsConfiguration()
}