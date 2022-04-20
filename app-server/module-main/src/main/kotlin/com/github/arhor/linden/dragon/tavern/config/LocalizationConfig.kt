package com.github.arhor.linden.dragon.tavern.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration(proxyBeanMethods = false)
class LocalizationConfig {

    @Bean(ERROR_MESSAGES_BEAN)
    fun errorMessages(): MessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            setBasename("classpath:error-messages")
            setDefaultEncoding("UTF-8")
        }
    }

    @Bean
    fun getValidator(@Qualifier(ERROR_MESSAGES_BEAN) errorMessages: MessageSource): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean().apply {
            setValidationMessageSource(errorMessages)
        }
    }

    companion object {
        const val ERROR_MESSAGES_BEAN = "errorMessages"
    }
}
