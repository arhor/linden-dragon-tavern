package org.arhor.diploma.startup

import org.arhor.diploma.core.ActionResult.Failure
import org.arhor.diploma.core.ActionResult.Success
import org.arhor.diploma.core.StartupTask
import org.arhor.diploma.core.Verifiable
import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import kotlin.system.exitProcess

@Component
class StartupVerifier(private val verifiers: List<Verifiable>) : StartupTask {

    companion object {
        @JvmStatic
        private val log: Logger = createLogger<StartupVerifier>()
    }

    override fun execute() {
        log.info("Starting app verification")
        log.info("Found [${verifiers.size}] verifiers to run")

        val width = DecimalFormat("0".repeat(verifiers.size.toString().length))

        val success = verifiers.sorted().mapIndexed { i, verifier ->
            val result = verifier.verify()
            when (result) {
                is Success -> log.info("${width.format(i)}: ${result.value}")
                is Failure -> log.error("${width.format(i)}: ${result.error.message}")
            }
            result
        }.all { it.isSuccess }

        if (!success) {
            log.error("An error occurred during startup verification")
            exitProcess(0)
        }

        log.info("App verification finished successfully")
    }
}