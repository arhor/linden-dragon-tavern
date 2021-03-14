package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.*
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(NON_NULL)
data class Skills(
    val athletics: Int?,
    val acrobatics: Int?,
    val sleightOfHand: Int?,
    val stealth: Int?,
    val arcana: Int?,
    val history: Int?,
    val investigation: Int?,
    val nature: Int?,
    val religion: Int?,
    val animalHandling: Int?,
    val insight: Int?,
    val medicine: Int?,
    val perception: Int?,
    val survival: Int?,
    val deception: Int?,
    val intimidation: Int?,
    val performance: Int?,
    val persuasion: Int?
)