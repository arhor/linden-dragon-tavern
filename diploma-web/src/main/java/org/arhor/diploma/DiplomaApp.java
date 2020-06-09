package org.arhor.diploma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
