package org.arhor.diploma.dnd.startup.verification

import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.Verifiable
import org.springframework.stereotype.Component

@Component
class VerifyDnDModuleAssets : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): ActionResult<String> {
        return ActionResult.success("Module [D&D] loaded")
    }
}