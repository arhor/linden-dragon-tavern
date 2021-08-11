package org.arhor.diploma.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.arhor.diploma.commons.Identifiable

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Monster(
    val name: String? = null,
    val size: String? = null,
    val type: String? = null,
    val subtype: String? = null,
    val alignment: String? = null,
    val armorClass: Int? = null,
    val hitPoints: String? = null,
    val speed: List<String>? = null,
    val abilities: Abilities? = null,
    val skills: Skills? = null,
    val damageVulnerabilities: List<String>? = null,
    val damageResistances: List<String>? = null,
    val damageImmunities: List<String>? = null,
    val conditionImmunities: List<String>? = null,
    val senses: List<String>? = null,
    val languages: List<String>? = null,
    val challengeRating: Double,
    val specialAbilities: List<SpecialAbility>? = null,
    val actions: List<Action>? = null,
    val legendaryActions: List<Action>? = null,
) : Identifiable<String> {

    @get:JsonIgnore
    override val id: String? = name
}