package org.arhor.diploma.config.props

import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration

@Component
class CustomProperties {
    final val error: ErrorProperties = ErrorProperties()
    final val cors: CorsConfiguration = CorsConfiguration()

    init {
        cors.applyPermitDefaultValues()
    }
}