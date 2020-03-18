package org.arhor.diploma.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.arhor.diploma.web.filter.CustomCsrfFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@Configuration
public class UtilBeansConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(5);
  }

  @Bean
  public MessageSource messageSource() {
    final var messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    return messageSource;
  }

  @Bean
  @Profile({"!dev"})
  public FilterRegistrationBean<CustomCsrfFilter> csrfFilter() {
    final var registrationBean = new FilterRegistrationBean<CustomCsrfFilter>();
    registrationBean.setFilter(new CustomCsrfFilter());
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
  }

  @Bean
  public Executor taskExecutor() {
    final int cores = Runtime.getRuntime().availableProcessors();

    final var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(cores);
    executor.setMaxPoolSize(cores * 2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("custom-task-executor-");
    executor.initialize();
    return executor;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }
}
