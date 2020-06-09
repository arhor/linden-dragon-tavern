package org.arhor.diploma.web.config;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.service.AccountService;
import org.arhor.diploma.web.filter.JwtAuthTokenFilter;
import org.arhor.diploma.web.security.JwtAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String SECURITY_POLICY_DIRECTIVES = String.join("; ",
      "default-src 'self'",
      "frame-src 'self' data:",
      "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com",
      "style-src 'self' 'unsafe-inline'",
      "img-src 'self' data:",
      "font-src 'self' data:"
  );

  private static final String FEATURE_POLICY_DIRECTIVES = String.join("; ",
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
  );

  private final JwtAuthEntryPoint unauthorizedHandler;
  private final JwtAuthTokenFilter jwtAuthTokenFilter;
  private final AccountService accountService;
  private final PasswordEncoder encoder;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService)
        .passwordEncoder(encoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
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
            .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
