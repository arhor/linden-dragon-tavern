package org.arhor.diploma.config

import org.arhor.diploma.service.AccountService
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.web.filter.CustomAuthFilter
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.lang.invoke.MethodHandles

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val accountService: AccountService,
    private val authFilter: CustomAuthFilter,
    private val authEntryPoint: AuthenticationEntryPoint,
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(accountService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults(ROLE_PREFIX)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers("/api/certificates").hasRole("ADMIN")// "ROLE_ADMIN"
            .anyRequest().permitAll()
            .and()
            .headers().contentSecurityPolicy(SECURITY_POLICY_DIRECTIVES)
            .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
            .featurePolicy(FEATURE_POLICY_DIRECTIVES)
            .and()
            .frameOptions().deny()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(authEntryPoint)
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(5)

    @Configuration
    class WebFilters {
        @Bean
        fun authFilter(provider: TokenProvider, accountService: AccountService): CustomAuthFilter {
            return CustomAuthFilter(provider, accountService)
        }

        @Bean
        fun corsFilter(): CorsFilter {
            val source = UrlBasedCorsConfigurationSource()
            val config = CorsConfiguration().apply { applyPermitDefaultValues() }

            if (config.allowedOrigins?.isNotEmpty() == true) {
                log.debug("Registering CORS filter")

                source.registerCorsConfiguration("/api/**", config)
                source.registerCorsConfiguration("/management/**", config)
                source.registerCorsConfiguration("/v2/api-docs", config)
            }

            return CorsFilter(source)
        }

        @Bean
        @Profile("!${SpringProfile.DEVELOPMENT}")
        fun csrfFilter(): FilterRegistrationBean<CustomCsrfFilter> {
            val csrfFilterBean = FilterRegistrationBean<CustomCsrfFilter>()

            csrfFilterBean.order = 1
            csrfFilterBean.filter = CustomCsrfFilter()
            csrfFilterBean.addUrlPatterns("/api/*")

            return csrfFilterBean
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

        const val ROLE_PREFIX = "ROLE_"

        private val SECURITY_POLICY_DIRECTIVES = arrayOf(
            "default-src 'self'",
            "frame-src 'self' data:",
            "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com",
            "style-src 'self' 'unsafe-inline'",
            "img-src 'self' data:",
            "font-src 'self' data:"
        ).joinToString(separator = "; ")

        private val FEATURE_POLICY_DIRECTIVES = arrayOf(
            "geolocation 'none'",
            "midi 'none'",
            "sync-xhr 'none'",
            "microphone 'none'",
            "camera 'none'",
            "magnetometer 'none'",
            "gyroscope 'none'",
            "speaker 'none'",
            "fullscreen 'self'",
            "payment 'none'"
        ).joinToString(separator = "; ")
    }
}
