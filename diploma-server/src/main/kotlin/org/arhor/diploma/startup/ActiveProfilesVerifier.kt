package org.arhor.diploma.startup

import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.util.SpringProfile.DEVELOPMENT
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ActiveProfilesVerifier(private val env: Environment) : Verifiable {

    private val devIncompatibleProfiles: Array<String> = arrayOf(
        SpringProfile.PRODUCTION,
        SpringProfile.CLOUD
    )

    override val priority: Int = 1

    override fun verify(): ActionResult<String> {
        if (env.activeProfiles.contains(DEVELOPMENT)) {
            for (profile in devIncompatibleProfiles) {
                if (env.activeProfiles.contains(profile)) {
                    return Failure(
                        IllegalStateException(
                            "Profiles '$DEVELOPMENT' and '${profile}' should not run both at the same time!"
                        )
                    )
                }
            }
        }
        return Success("There are no mutually exclusive profiles.")
    }

}