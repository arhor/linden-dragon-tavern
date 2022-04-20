package com.github.arhor.linden.dragon.tavern.dnd.startup.verification

import com.github.arhor.linden.dragon.tavern.common.Priority
import com.github.arhor.linden.dragon.tavern.common.Verifiable
import org.springframework.stereotype.Component

@Component
class VerifyDnDModuleAssets : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): Result<String> {
        return Result.success("Module [D&D] loaded")
    }
}
