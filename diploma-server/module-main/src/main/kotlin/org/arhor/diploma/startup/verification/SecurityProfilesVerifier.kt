package org.arhor.diploma.startup.verification

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.arhor.diploma.Role
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.Verifiable
import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SecurityProfilesVerifier(
    private val securityProfileRepository: SecurityProfileRepository,
) : Verifiable {

    override val priority = Priority.NORMAL

    override fun verify(): Result<String> = runCatching {
        val createdProfiles = runBlocking { createMissingProfiles() }

        if (createdProfiles.isNotEmpty()) {
            "The following security profiles were created: $createdProfiles"
        } else {
            "All security profiles are in consistency with DB."
        }
    }

    private suspend fun createMissingProfiles(): List<String> {
        val allRoles = Role.values().filter { it.persistent }.map { it.name }.toList()
        val existingRoles = securityProfileRepository.findAllByNames(allRoles).map { it.name }.toList()

        val createdProfiles = allRoles.subtract(existingRoles)
            .map { roleToCreate -> SecurityProfile(name = roleToCreate, isSynthetic = true) }
            .let { newProfiles -> securityProfileRepository.saveAll(newProfiles) }

        return createdProfiles.mapNotNull { it.name }.toList()
    }
}
