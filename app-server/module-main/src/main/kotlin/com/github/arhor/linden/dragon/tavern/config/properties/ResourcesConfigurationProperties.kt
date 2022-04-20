package com.github.arhor.linden.dragon.tavern.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("configuration.resources")
data class ResourcesConfigurationProperties(val patterns: List<String>, val locations: List<String>) {

    fun patterns(): Array<String> = patterns.toTypedArray()

    fun locations(): Array<String> = locations.toTypedArray()
}
