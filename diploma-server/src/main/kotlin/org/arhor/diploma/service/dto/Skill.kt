package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.core.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skill(
    override var id: Int?,
    var name: String?,
    var description: String?,
    var abilityScore: String?
) : Identifiable<Int>