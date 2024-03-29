package com.github.arhor.linden.dragon.tavern.dnd.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Abilities(
    @JsonProperty("STR") val str: Int,
    @JsonProperty("DEX") val dex: Int,
    @JsonProperty("CON") val con: Int,
    @JsonProperty("INT") val int: Int,
    @JsonProperty("WIS") val wis: Int,
    @JsonProperty("CHA") val cha: Int
)
