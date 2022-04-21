package com.github.arhor.linden.dragon.tavern

import com.github.arhor.linden.dragon.tavern.infrastructure.startup.StartupTask
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(proxyBeanMethods = false)
@ConfigurationPropertiesScan("com.github.arhor.linden.dragon.tavern.config")
class Application(private val startupTasks: List<StartupTask>) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        for (task in startupTasks) {
            task.execute()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
