package org.arhor.diploma.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class JacksonConfig {

    @Bean
    fun javaTimeModule(): Module = JavaTimeModule()

    @Bean
    fun jdk8TimeModule(): Module = Jdk8Module()

    @Bean
    fun kotlinModule(): Module = KotlinModule()
}