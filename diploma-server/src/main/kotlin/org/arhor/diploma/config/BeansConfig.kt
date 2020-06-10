package org.arhor.diploma.config

import org.arhor.diploma.util.Converter
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration as MapperConfig
import org.modelmapper.convention.MatchingStrategies
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeansConfig {

  @Bean
  fun modelMapper(): ModelMapper {
    return object : ModelMapper() {
      init {
        configuration.matchingStrategy = MatchingStrategies.LOOSE
        configuration.fieldAccessLevel = MapperConfig.AccessLevel.PRIVATE
        configuration.isFieldMatchingEnabled = true
        configuration.isSkipNullEnabled = true
      }
    }
  }

  @Bean
  fun modelMapperConverter(mapper: ModelMapper): Converter {
    return object : Converter {
      override fun <T, R> convert(item: T, targetClass: Class<R>): R {
        return mapper.map(item, targetClass)
      }
    }
  }

  @Bean
  fun messageSource(): MessageSource {
    val messageSource = ResourceBundleMessageSource()
    messageSource.setBasename("messages")
    return messageSource
  }

  @Bean
  @Profile("!dev")
  fun csrfFilter(csrfFilter: CustomCsrfFilter): FilterRegistrationBean<CustomCsrfFilter> {
    return object : FilterRegistrationBean<CustomCsrfFilter>() {
      init {
        order = 1
        filter = csrfFilter
        addUrlPatterns("/api/*")
      }
    }
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder(5)
  }
}
