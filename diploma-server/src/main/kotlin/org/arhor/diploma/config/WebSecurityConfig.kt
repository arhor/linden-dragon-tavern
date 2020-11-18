package org.arhor.diploma.config

import org.arhor.diploma.service.AccountService
import org.arhor.diploma.web.security.JWTConfigurer
import org.arhor.diploma.web.security.JwtAuthEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val unauthorizedHandler: JwtAuthEntryPoint,
    private val jwtConfigurer: JWTConfigurer,
    private val accountService: AccountService,
    private val encoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(accountService)
            .passwordEncoder(encoder)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors()
            .and()
            .headers()
            .contentSecurityPolicy(SECURITY_POLICY_DIRECTIVES)
            .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
            .featurePolicy(FEATURE_POLICY_DIRECTIVES)
            .and()
            .frameOptions()
            .deny()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .apply(jwtConfigurer)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    companion object {
        @JvmStatic
        private val SECURITY_POLICY_DIRECTIVES = arrayOf(
            "default-src 'self'",
            "frame-src 'self' data:",
            "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com",
            "style-src 'self' 'unsafe-inline'",
            "img-src 'self' data:",
            "font-src 'self' data:"
        ).joinToString(separator = "; ")

        @JvmStatic
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
