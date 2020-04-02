package org.arhor.diploma.config;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthEntryPoint unauthorizedHandler;
  private final JwtAuthTokenFilter jwtAuthTokenFilter;
  private final UserService userService;
  private final PasswordEncoder encoder;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
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
        .sessionManagement().sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests().anyRequest().permitAll()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .httpBasic()
        .and()
        .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
