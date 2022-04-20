package com.github.arhor.linden.dragon.tavern.infrastructure.startup.tasks.verification

import com.github.arhor.linden.dragon.tavern.Role
import com.github.arhor.linden.dragon.tavern.common.Priority
import com.github.arhor.linden.dragon.tavern.common.Verifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.SecurityProfile
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.SecurityProfileRepository
import org.springframework.stereotype.Component

@Component
class SecurityProfilesVerifier(
    private val securityProfileRepository: SecurityProfileRepository,
) : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): Result<String> = runCatching {
        val createdProfiles = createMissingProfiles()

        if (createdProfiles.isNotEmpty()) {
            "The following security profiles were created: $createdProfiles"
        } else {
            "All security profiles are in consistency with DB."
        }
    }

    private fun createMissingProfiles(): List<String> {
        val allRoles = Role.values().filter { it.persistent }.map { it.name }.toList()
        val existingRoles = securityProfileRepository.findAllByNames(allRoles).map { it.name }.toList()

        val createdProfiles = allRoles.subtract(existingRoles)
            .map { roleToCreate -> SecurityProfile(name = roleToCreate, isSynthetic = true) }
            .let { newProfiles -> securityProfileRepository.saveAll(newProfiles) }

        return createdProfiles.mapNotNull { it.name }.toList()
    }
}
