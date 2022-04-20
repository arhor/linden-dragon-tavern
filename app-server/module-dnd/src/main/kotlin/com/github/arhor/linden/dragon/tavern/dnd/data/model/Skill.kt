package com.github.arhor.linden.dragon.tavern.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.arhor.linden.dragon.tavern.common.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skill(
    var name: String?,
    var description: String?,
    var abilityScore: String?
) : Identifiable<String> {

    @get:JsonIgnore
    override val id: String? = name
}
