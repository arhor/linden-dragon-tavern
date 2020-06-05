package org.arhor.diploma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class DiplomaApp {

  public static void main(String[] args) {
    SpringApplication.run(DiplomaApp.class, args);
  }

  @Bean
  public CommandLineRunner run() {
    return args -> {};
  }
}
