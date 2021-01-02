package org.arhor.diploma.config

import org.springframework.beans.factory.config.MethodInvokingFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class MethodInvokingFactoryBeanConfig {

    // In order to make `SecurityContextHolder` available in your aspect, it is required
    // to configure the Spring’s method invoking factory bean to make the Security Context
    // Holder available from the main execution thread.
    @Bean
    fun methodInvokingFactoryBean(): MethodInvokingFactoryBean {
        val factory = MethodInvokingFactoryBean()
        factory.targetClass = SecurityContextHolder::class.java
        factory.targetMethod = "setStrategyName"
        factory.setArguments(listOf(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL))
        return factory
    }
}