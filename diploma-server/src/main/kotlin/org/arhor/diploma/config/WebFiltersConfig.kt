package org.arhor.diploma.config

import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.web.filter.CustomAuthFilter
import org.arhor.diploma.web.filter.CustomCorsFilter
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.arhor.diploma.web.security.TokenProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class WebFiltersConfig {

    @Bean
//    @Profile("!${SpringProfile.DEVELOPMENT}")
    fun customCsrfFilter(): FilterRegistrationBean<CustomCsrfFilter> {
        val csrfFilterBean = FilterRegistrationBean<CustomCsrfFilter>()

        csrfFilterBean.order = 1
        csrfFilterBean.filter = CustomCsrfFilter()
        csrfFilterBean.addUrlPatterns("/api/*")

        return csrfFilterBean
    }

    @Bean
    fun customAuthFilter(
        provider: TokenProvider<*>,
        @Qualifier("accountServiceImpl") service: UserDetailsService
    ): CustomAuthFilter {
        return CustomAuthFilter(provider, service)
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun customCorsFilter(): CustomCorsFilter {
        return CustomCorsFilter()
    }
}