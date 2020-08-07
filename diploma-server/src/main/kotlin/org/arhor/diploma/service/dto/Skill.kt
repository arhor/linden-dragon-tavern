package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.core.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skill(
    var name: String?,
    var description: String?,
    var abilityScore: String?
) : Identifiable<String> {

    override fun getId(): String? {
        return name
    }
}