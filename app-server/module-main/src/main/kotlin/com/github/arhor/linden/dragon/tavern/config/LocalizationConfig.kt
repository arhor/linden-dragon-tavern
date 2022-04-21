package com.github.arhor.linden.dragon.tavern.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration(proxyBeanMethods = false)
class LocalizationConfig {

    @Bean
    fun messages(webProperties: WebProperties): MessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            setBasename("classpath:messages")
            setDefaultEncoding("UTF-8")
            setDefaultLocale(checkNotNull(webProperties.locale) { "spring.web.locale cannot be null" })
        }
    }

    @Bean
    fun getValidator(messages: MessageSource): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean().apply {
            setValidationMessageSource(messages)
        }
    }
}
