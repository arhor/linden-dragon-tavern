package org.arhor.diploma

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DiplomaApp {

  @Bean
  fun run() = CommandLineRunner { args ->
    for (arg in args) {
      println(arg)
    }
  }
}

fun main(args: Array<String>) {
  runApplication<DiplomaApp>(*args)
}