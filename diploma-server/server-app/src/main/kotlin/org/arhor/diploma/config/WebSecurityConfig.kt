package org.arhor.diploma.config

import kotlinx.coroutines.reactor.mono
import org.arhor.diploma.Roles
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
class WebSecurityConfig {

    @Bean
    fun userDetailsService(
        accRepository: AccountRepository,
        secRepository: SecurityProfileRepository
    ): ReactiveUserDetailsService {

        return object : ReactiveUserDetailsService {

            override fun findByUsername(username: String): Mono<UserDetails> {
                return mono {
                    accRepository
                        .findByUsername(username)?.let { account ->
                            val status = !account.isDeleted
                            User(
                                account.username,
                                account.password,
                                status,
                                status,
                                status,
                                status,
                                extractAuthorities(account)
                            )
                        } ?: throw UsernameNotFoundException(username)
                }
            }

            private suspend fun extractAuthorities(account: Account): Collection<GrantedAuthority> {
                val securityProfile = secRepository.findByAccountId(account.id!!)

                val authorities = when {
                    securityProfile == null -> emptyList()
                    securityProfile.isSynthetic -> listOf("ROLE_${securityProfile.name}")
                    else -> listOf(Roles.USER.prefixed()) // TODO: extract real profile authorities
                }

                return authorities.map { SimpleGrantedAuthority(it) }
            }
        }
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange()
            .anyExchange().permitAll()
            .and()
            .httpBasic()
            .and()
            .formLogin().disable()

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(5)
}

//private val SECURITY_POLICY_DIRECTIVES = arrayOf(
//    "default-src 'self'",
//    "frame-src 'self' data:",
//    "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com",
//    "style-src 'self' 'unsafe-inline'",
//    "img-src 'self' data:",
//    "font-src 'self' data:"
//).joinToString(separator = "; ")
//
//private val FEATURE_POLICY_DIRECTIVES = arrayOf(
//    "geolocation 'none'",
//    "midi 'none'",
//    "sync-xhr 'none'",
//    "microphone 'none'",
//    "camera 'none'",
//    "magnetometer 'none'",
//    "gyroscope 'none'",
//    "speaker 'none'",
//    "fullscreen 'self'",
//    "payment 'none'"
//).joinToString(separator = "; ")
//
//private val logger = KotlinLogging.logger {}
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//class WebSecurityConfig(
//    private val accountService: AccountService,
//    private val authFilter: CustomAuthFilter,
//    private val authEntryPoint: AuthenticationEntryPoint,
//) : WebSecurityConfigurerAdapter() {
//
//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(accountService)
//            .passwordEncoder(passwordEncoder())
//    }
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.csrf().disable()
//            .cors()
//            .and()
//            .authorizeRequests()
//            .anyRequest().permitAll()
//            .and()
//            .headers().contentSecurityPolicy(SECURITY_POLICY_DIRECTIVES)
//            .and()
//            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
//            .and()
//            .featurePolicy(FEATURE_POLICY_DIRECTIVES)
//            .and()
//            .frameOptions().deny()
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .exceptionHandling().authenticationEntryPoint(authEntryPoint)
//            .and()
//            .httpBasic().disable()
//            .formLogin().disable()
//            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
//    }
//
//    @Configuration
//    class WebFilters {
//        @Bean
//        fun authFilter(provider: TokenProvider, accountService: AccountService): CustomAuthFilter {
//            return CustomAuthFilter(provider, accountService)
//        }
//
//        @Bean
//        fun corsFilter(): CorsFilter {
//            val source = UrlBasedCorsConfigurationSource()
//            val config = CorsConfiguration().apply { applyPermitDefaultValues() }
//
//            if (config.allowedOrigins?.isNotEmpty() == true) {
//                source.registerCorsConfiguration("/api/**", config)
//                source.registerCorsConfiguration("/management/**", config)
//                source.registerCorsConfiguration("/v2/api-docs", config)
//            }
//
//            return CorsFilter(source)
//        }
//
//        @Bean
//        @Profile("!${SpringProfile.DEVELOPMENT}")
//        fun csrfFilter(): FilterRegistrationBean<CustomCsrfFilter> {
//
//            return FilterRegistrationBean<CustomCsrfFilter>().apply {
//                order = 1
//                filter = CustomCsrfFilter()
//                addUrlPatterns("/api/*")
//            }
//        }
//    }
//}
