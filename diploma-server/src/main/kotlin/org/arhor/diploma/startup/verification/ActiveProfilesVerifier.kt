package org.arhor.diploma.startup.verification

import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.util.SpringProfile
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ActiveProfilesVerifier(private val env: Environment) : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): ActionResult<String> {
        if (env.activeProfiles.contains(SpringProfile.DEVELOPMENT)) {
            for (profile in env.activeProfiles) {
                if ((profile == SpringProfile.PRODUCTION) || (profile == SpringProfile.CLOUD)) {
                    return Failure(
                        IllegalStateException(
                            "Profiles '${SpringProfile.DEVELOPMENT}' and '${profile}' should not run both at the same time!"
                        )
                    )
                }
            }
        }
        return Success("There are no mutually exclusive profiles.")
    }
}