package org.arhor.diploma.startup.verification

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

    private val mutuallyExclusiveProfiles: Map<String, Set<String>> = mapOf(
        DEVELOPMENT to hashSetOf(PRODUCTION, CLOUD, TEST),
    )

    override fun verify(): Result<String> {
        for (baseProfile in env.activeProfiles) {
            val forbiddenProfiles = mutuallyExclusiveProfiles[baseProfile] ?: emptySet()
            for (otherProfile in env.activeProfiles) {
                if (forbiddenProfiles.contains(otherProfile)) {
                    return Result.failure(
                        IllegalStateException(
                            "Profiles '${baseProfile}' and '${otherProfile}' should not run both at the same time!"
                        )
                    )
                }
            }
        }
        return Result.success("There are no mutually exclusive profiles.")
    }
}