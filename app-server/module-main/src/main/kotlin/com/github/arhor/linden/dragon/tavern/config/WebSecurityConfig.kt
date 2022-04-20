package com.github.arhor.linden.dragon.tavern.config

import com.github.arhor.linden.dragon.tavern.Role
import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.session.MapSessionRepository
import org.springframework.session.SessionRepository
import java.util.concurrent.ConcurrentHashMap

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration(proxyBeanMethods = false)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
//            .and()
//            .httpBasic().securityContextRepository(WebSessionServerSecurityContextRepository())
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler())
            .and()
            .formLogin().disable()
//            .oauth2Login()
//            .and()
            .headers()
            .xssProtection()
            .and()
            .contentSecurityPolicy("script-src 'self' 'unsafe-inline'")
    }

    @Bean
    fun sessionRepository(): SessionRepository<*> {
        return MapSessionRepository(ConcurrentHashMap())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(5)
    }

    @Bean
    @Profile("dev")
    @Primary
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        logger.debug("Initializing UserDetailsService with predefined `user` and `admin` accounts")

        val user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user"))
            .roles(Role.USER.name)
            .build()

        val admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(Role.ADMIN.name)
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }

    companion object : KLogging()
}
