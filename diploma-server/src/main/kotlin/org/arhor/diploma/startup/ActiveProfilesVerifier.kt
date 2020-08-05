package org.arhor.diploma.startup

import org.arhor.diploma.core.ActionResult
import org.arhor.diploma.core.ActionResult.Failure
import org.arhor.diploma.core.ActionResult.Success

import org.arhor.diploma.core.Verifiable
import org.arhor.diploma.util.SpringProfile
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
        if (env.activeProfiles.contains(SpringProfile.DEVELOPMENT)) {
            for (profile in devIncompatibleProfiles) {
                if (env.activeProfiles.contains(profile)) {
                    val error = createError(profile)
                    return Failure(error)
                }
            }
        }

        return Success("There are no mutually exclusive profiles.")
    }

    private fun createError(p2: String): Throwable {
        return IllegalStateException(
            "Profiles '${SpringProfile.DEVELOPMENT}' and '${p2}' should not run both at the same time!"
        )
    }
}