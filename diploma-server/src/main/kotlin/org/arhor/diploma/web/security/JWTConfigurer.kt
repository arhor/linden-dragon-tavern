package org.arhor.diploma.web.security

import org.arhor.diploma.web.filter.CustomAuthFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class JWTConfigurer(
    private val authFilter: CustomAuthFilter
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}