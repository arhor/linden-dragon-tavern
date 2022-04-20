package com.github.arhor.linden.dragon.tavern.infrastructure.startup.tasks.verification

import com.github.arhor.linden.dragon.tavern.common.Priority
import com.github.arhor.linden.dragon.tavern.common.Verifiable
import org.springframework.stereotype.Component

@Component
class SimpleStartupVerifier : Verifiable {

    override val priority = Priority.FIRST

    override fun verify(): Result<String> {
        return Result.success("Simple startup verification complete. It should be always first.")
    }
}
