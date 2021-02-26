package org.arhor.diploma.startup.verification

import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.web.model.ErrorCode
import org.springframework.stereotype.Component

@Component
class ErrorCodesVerifier : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): ActionResult<String> {
        val result = mutableMapOf<ErrorCode.Type, Pair<Int, Int>>()

        ErrorCode.values().groupBy { it.type }.forEach { (type, errorCodes) ->
            errorCodes.groupingBy { it.code }.eachCount()
                .filter { it.value > 1 }
                .forEach { (code, numberOfDuplicates) ->
                    result[type] = code to numberOfDuplicates
                }
        }

        if (result.isNotEmpty()) {
            return Failure(
                IllegalStateException(
                    "Error code duplicates found: $result"
                )
            )
        }

        return Success("Error code duplicates not found.")
    }
}