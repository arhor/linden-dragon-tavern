package org.arhor.diploma

import org.arhor.diploma.commons.StartupTask
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiplomaApp(private val startupTasks: List<StartupTask>) : CommandLineRunner {

    override fun run(vararg args: String) {
        startupTasks.sorted().forEach(StartupTask::execute)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<DiplomaApp>(*args)
        }
    }
}
