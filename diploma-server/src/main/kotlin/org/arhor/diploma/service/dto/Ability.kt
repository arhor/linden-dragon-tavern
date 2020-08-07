package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.core.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ability(
    override var id: Int?,
    var name: String?,
    var fullName: String?,
    var description: List<String>,
    var skills: List<String>
) : Identifiable<Int>