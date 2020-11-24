package org.arhor.diploma.config

import org.arhor.diploma.config.props.CustomProperties
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.web.filter.CustomAuthFilter
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.lang.invoke.MethodHandles

@Configuration
class WebFiltersConfig {

    @Bean
    @Profile("!${SpringProfile.DEVELOPMENT}")
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
    fun corsFilter(props: CustomProperties): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config: CorsConfiguration = props.cors

        if (config.allowedOrigins?.isNotEmpty() == true) {
            log.debug("Registering CORS filter")

            val corsPath = arrayOf(
                "/api/**",
                "/management/**",
                "/v2/api-docs"
            )

            corsPath.forEach { path ->
                source.registerCorsConfiguration(path, config)
            }
        }

        return CorsFilter(source)
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}