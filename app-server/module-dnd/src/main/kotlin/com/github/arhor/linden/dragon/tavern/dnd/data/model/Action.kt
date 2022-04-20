package com.github.arhor.linden.dragon.tavern.dnd.data.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class Action(
    val name: String,
    val desc: String,
    val attackBonus: Int?,
    val damageBonus: Int?,
    val damageDice: String?
)
