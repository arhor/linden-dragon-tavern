package org.arhor.diploma.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties