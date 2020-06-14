package org.arhor.diploma

import org.arhor.diploma.util.Converter
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.context.support.beans

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