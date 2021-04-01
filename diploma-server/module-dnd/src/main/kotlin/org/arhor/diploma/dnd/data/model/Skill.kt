package org.arhor.diploma.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.commons.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skill(
    var name: String?,
    var description: String?,
    var abilityScore: String?
) : Identifiable<String> {

    override val id: String?
        @JsonIgnore
        get() = name
}