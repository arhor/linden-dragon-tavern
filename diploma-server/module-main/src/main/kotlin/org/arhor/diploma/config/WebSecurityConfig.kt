package org.arhor.diploma.config

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

private val logger = KotlinLogging.logger {}

@EnableWebFluxSecurity
class WebSecurityConfig {

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
