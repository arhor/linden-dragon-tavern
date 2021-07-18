package org.arhor.diploma

import org.arhor.diploma.commons.StartupTask
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(proxyBeanMethods = false)
class DiplomaApp(private val startupTasks: List<StartupTask>) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        startupTasks.sorted().forEach(StartupTask::execute)
    }
}

fun main(args: Array<String>) {
    runApplication<DiplomaApp>(*args)
}
