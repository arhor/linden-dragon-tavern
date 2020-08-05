package org.arhor.diploma.startup

import org.arhor.diploma.core.ActionResult
import org.arhor.diploma.core.ActionResult.Success
import org.arhor.diploma.core.Verifiable
import org.springframework.stereotype.Component

@Component
class SimpleStartupVerifier : Verifiable {

    override val priority: Int = 0

    override fun verify(): ActionResult<String> {
        return Success("Simple startup verification complete. It should be always first.")
    }
}