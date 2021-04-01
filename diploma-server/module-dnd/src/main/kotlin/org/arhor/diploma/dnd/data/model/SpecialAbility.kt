package org.arhor.diploma.dnd.data.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class SpecialAbility(
    val name: String,
    val desc: String,
    val damageDice: String?
)