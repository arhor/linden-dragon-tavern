package org.arhor.diploma.config

import mu.KotlinLogging
import org.arhor.diploma.Role
import org.arhor.diploma.Role.ADMIN
import org.arhor.diploma.Role.USER
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.session.ReactiveMapSessionRepository
import org.springframework.session.ReactiveSessionRepository
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

@EnableWebFluxSecurity
@EnableSpringWebSession
@EnableReactiveMethodSecurity
class WebSecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange()
            .anyExchange().permitAll()
            .and()
            .httpBasic().securityContextRepository(WebSessionServerSecurityContextRepository())
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(HttpStatusReturningServerLogoutSuccessHandler())
            .and()
            .formLogin().disable()
            .csrf().disable()
            .build()
    }

    @Bean
    fun sessionRepository(): ReactiveSessionRepository<*> {
        return ReactiveMapSessionRepository(ConcurrentHashMap())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(5)

    @Bean
    @ConditionalOnMissingBean
    fun userDetailsService(passwordEncoder: PasswordEncoder): MapReactiveUserDetailsService {
        logger.debug("Initializing UserDetailsService with predefined `user` and `admin` accounts")

        val user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user"))
            .roles(USER)
            .build()

        val admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .roles(ADMIN)
            .build()

        return MapReactiveUserDetailsService(user, admin)
    }

    private fun User.UserBuilder.roles(vararg roles: Role): User.UserBuilder {
        return this.roles(*roles.map { it.name }.toTypedArray())
    }
}
