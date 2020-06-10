package org.arhor.diploma.config

import lombok.RequiredArgsConstructor
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.web.filter.JwtAuthTokenFilter
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val unauthorizedHandler: JwtAuthEntryPoint,
    private val jwtAuthTokenFilter: JwtAuthTokenFilter,
    private val accountService: AccountService,
    private val encoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

  companion object {
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

  override fun configure(auth: AuthenticationManagerBuilder) {java.lang.String.join(",", "")
    auth.userDetailsService(accountService)
        .passwordEncoder(encoder)
  }

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
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
            .httpBasic()
        .and()
            .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
  }
}
