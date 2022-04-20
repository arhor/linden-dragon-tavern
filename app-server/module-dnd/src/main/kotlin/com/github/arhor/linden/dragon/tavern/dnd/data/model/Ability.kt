package com.github.arhor.linden.dragon.tavern.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.arhor.linden.dragon.tavern.common.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ability(
    var name: String?,
    var fullName: String?,
    var description: List<String>,
    var skills: List<String>
) : Identifiable<String> {

    @get:JsonIgnore
    override val id: String? = name
}
