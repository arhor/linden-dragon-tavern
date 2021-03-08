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
        val duplicates = seekForDuplicates(ErrorCode.values())

        return if (duplicates.isNotEmpty()) {
            Failure(IllegalStateException("Error code duplicates found: ${duplicates}."))
        } else {
            Success("Error code duplicates not found.")
        }
    }

    private fun seekForDuplicates(errorCodes: Array<ErrorCode>): List<String> {
        val messages = mutableListOf<String>()

        for ((type, errorsByType) in errorCodes.groupBy { it.type }) {
            for ((code, errorsByCode) in errorsByType.groupBy { it.code }) {
                if (errorsByCode.size > 1) {
                    messages.add(errorMessage(type, code, errorsByCode))
                }
            }
        }
        return messages
    }

    private fun errorMessage(type: ErrorCode.Type, code: Int, duplicates: List<ErrorCode>): String {
        return "Type: ${type}, Code: ${code}, ErrorCodes: ${duplicates.map { it.name }}"
    }
}