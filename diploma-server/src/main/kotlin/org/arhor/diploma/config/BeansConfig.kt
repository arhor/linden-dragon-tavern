package org.arhor.diploma.config

import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeansConfig {

    @Bean
    @Profile("!${SpringProfile.DEVELOPMENT}")
    fun csrfFilter(csrfFilter: CustomCsrfFilter) = FilterRegistrationBean<CustomCsrfFilter>().apply {
        order = 1
        filter = csrfFilter
        addUrlPatterns("/api/*")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(5)
}
