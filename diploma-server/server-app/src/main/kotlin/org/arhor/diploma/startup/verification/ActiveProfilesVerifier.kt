package org.arhor.diploma.startup.verification

import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.util.SpringProfile.CLOUD
import org.arhor.diploma.util.SpringProfile.DEVELOPMENT
import org.arhor.diploma.util.SpringProfile.PRODUCTION
import org.arhor.diploma.util.SpringProfile.TEST
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ActiveProfilesVerifier(private val env: Environment) : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): ActionResult<String> {
        for (baseProfile in env.activeProfiles) {
            val forbiddenProfiles = mutuallyExclusiveProfiles[baseProfile]
            for (otherProfile in env.activeProfiles) {
                if (forbiddenProfiles?.contains(otherProfile) == true) {
                    return failure(baseProfile, otherProfile)
                }
            }
        }
        return Success("There are no mutually exclusive profiles.")
    }

    private fun failure(baseProfile: String, otherProfile: String): Failure {
        return Failure(
            IllegalStateException(
                "Profiles '${baseProfile}' and '${otherProfile}' should not run both at the same time!"
            )
        )
    }

    private val mutuallyExclusiveProfiles: Map<String, Set<String>> = mapOf(
        DEVELOPMENT to hashSetOf(PRODUCTION, CLOUD, TEST),
    )
}