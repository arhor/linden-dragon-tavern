package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.*

@JsonInclude(NON_NULL)
data class SpecialAbility(
    val name: String,
    val desc: String,
    val damageDice: String?
)