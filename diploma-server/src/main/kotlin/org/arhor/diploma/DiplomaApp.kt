package org.arhor.diploma

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DiplomaApp {

  @Bean
  fun run() = CommandLineRunner {
    for (arg in it) {
      println(arg)
    }
  }
}

fun main(args: Array<String>) {
  runApplication<DiplomaApp>(*args)
}