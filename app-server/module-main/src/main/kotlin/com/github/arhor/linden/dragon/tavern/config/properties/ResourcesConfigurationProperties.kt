package com.github.arhor.linden.dragon.tavern.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("configuration.resources")
class ResourcesConfigurationProperties(
    /**
     * Path patterns for the static resources.
     */
    val patterns: Array<out String>,

    /**
     * Locations to lookup for the static resources.
     */
    val locations: Array<out String>,
)
