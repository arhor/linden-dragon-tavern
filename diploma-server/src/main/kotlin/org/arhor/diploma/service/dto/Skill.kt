package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skill(
    override var id: Int?,
    var name: String?,
    var description: String?,
    var abilityScore: String?
) : DTO<Int>