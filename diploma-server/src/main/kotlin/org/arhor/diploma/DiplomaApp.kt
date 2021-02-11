package org.arhor.diploma

import org.arhor.diploma.commons.StartupTask
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<DiplomaApp>(*args)
}

@SpringBootApplication
class DiplomaApp(private val startupTasks: List<StartupTask>) : CommandLineRunner {

    override fun run(vararg args: String) {
        startupTasks.sorted().forEach(StartupTask::execute)
    }
}
