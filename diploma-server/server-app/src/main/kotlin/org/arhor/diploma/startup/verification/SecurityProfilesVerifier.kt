package org.arhor.diploma.startup.verification

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.mono
import org.arhor.diploma.Roles
import org.arhor.diploma.commons.ActionResult
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.springframework.stereotype.Component

@Component
class SecurityProfilesVerifier(
    private val securityProfileRepository: SecurityProfileRepository
) : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): ActionResult<String> {
        return mono {
            try {
                val allRoles = Roles.values().filter { it.persistent }.map { it.name }.toList()
                val profiles = securityProfileRepository.findAllByNames(allRoles).map { it.name }.toList()

                val createdProfiles = allRoles.subtract(profiles)
                    .map { roleToCreate -> SecurityProfile(id = null, name = roleToCreate, isSynthetic = true) }
                    .let { newProfiles -> securityProfileRepository.saveAll(newProfiles) }
                    .map { it.name }
                    .toList()

                val message =
                    createdProfiles.takeIf { it.isNotEmpty() }
                        ?.let { "The following security profiles were created: [${it.joinToString()}]" }
                        ?: "All security profiles are in consistency with DB."

                Success(message)
            } catch (ex: Exception) {
                Failure(ex)
            }
        }.block()!!
    }
}