package org.arhor.diploma.startup

import mu.KotlinLogging
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.StartupTask
import org.arhor.diploma.commons.Verifiable
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component
import java.text.DecimalFormat

private val logger = KotlinLogging.logger {}

@Component
class VerifyApp(
    private val verifiers: List<Verifiable>,
    private val ctx: ConfigurableApplicationContext,
) : StartupTask {

    init {
        checkVerifiers()
    }

    override val priority = Priority.HIGH

    override fun execute() {
        logger.info("Starting app verification")
        logger.info { "Found [${verifiers.size}] verifiers to run" }

        val width = DecimalFormat("0".repeat(verifiers.size.toString().length))

        val success = verifiers.sorted().mapIndexed { i, verifier ->
            val result = verifier.verify()
            val num = width.format(i + 1)
            when (result) {
                is Success -> logger.info  { "${num}: [SUCCESS] ${result.value}" }
                is Failure -> logger.error { "${num}: [FAILURE] ${result.error.message}" }
            }
            result
        }.all { it.isSuccess }

        if (!success) {
            logger.error("An error occurred during startup verification")
            ctx.close()
        }

        logger.info("App verification finished successfully")
    }

    private fun checkVerifiers() {
        verifiers
            .groupBy { it.priority }
            .filterKeys { it == Priority.FIRST || it == Priority.LAST }
            .filterValues { it.size > 1 }
            .forEach { (priority, samePriorityVerifiers) ->
                logger.warn { duplicatesMsg(priority, samePriorityVerifiers) }
            }
    }

    private fun duplicatesMsg(priority: Priority, verifiers: List<Verifiable>): String {
        val duplicates = verifiers.map { it::class.simpleName }.joinToString()
        return  "There are several startup verifiers with priority `${priority}`: [${duplicates}]"
    }
}