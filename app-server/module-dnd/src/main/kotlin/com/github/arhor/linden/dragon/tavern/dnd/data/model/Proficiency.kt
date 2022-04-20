package com.github.arhor.linden.dragon.tavern.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.github.arhor.linden.dragon.tavern.common.Identifiable

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Proficiency(
    val name: String,
    val type: String,
    val classes: List<String>,
    val races: List<String>,
) : Identifiable<String> {

    @get:JsonIgnore
    override val id: String = name
}
