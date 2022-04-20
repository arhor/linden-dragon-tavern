package com.github.arhor.linden.dragon.tavern.infrastructure.startup.tasks.verification

import com.github.arhor.linden.dragon.tavern.common.Priority
import com.github.arhor.linden.dragon.tavern.common.Verifiable
import com.github.arhor.linden.dragon.tavern.web.error.ErrorCode
import org.springframework.stereotype.Component

@Component
class ErrorCodesVerifier : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): Result<String> {
        val duplicates = seekForDuplicates(ErrorCode.values())

        return if (duplicates.isNotEmpty()) {
            Result.failure(IllegalStateException("Error code duplicates found: ${duplicates}."))
        } else {
            Result.success("Error code duplicates not found.")
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
