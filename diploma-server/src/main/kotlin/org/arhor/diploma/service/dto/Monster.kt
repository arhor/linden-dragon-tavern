package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.*
import org.arhor.diploma.core.Identifiable

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Monster(
    override var id: Int?,
    val name: String,
    val size: String,
    val type: String,
    val subtype: String?,
    val alignment: String,
    val armorClass: Int,
    val hitPoints: Int,
    val hitDice: String,
    val speed: List<String>,
    val abilities: Abilities,
    val skills: Skills,
    val damageVulnerabilities: List<String>?,
    val damageResistances: List<String>?,
    val damageImmunities: List<String>?,
    val conditionImmunities: List<String>?,
    val senses: List<String>,
    val languages: List<String>?,
    val challengeRating: Double,
    val specialAbilities: List<SpecialAbility>?,
    val actions: List<Action>?,
    val legendaryActions: List<Action>?
) : Identifiable<Int>