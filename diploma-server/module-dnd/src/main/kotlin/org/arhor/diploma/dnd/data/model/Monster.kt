package org.arhor.diploma.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.arhor.diploma.commons.Identifiable

data class Monster(
    val name: String,
    val size: String,
    val type: String,
    val challengeRating: Double
) : Identifiable<String> {

    override val id: String
        @JsonIgnore
        get() = name

    @JsonInclude(NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Details(
        val name: String,
        val size: String,
        val type: String,
        val subtype: String?,
        val alignment: String,
        val armorClass: Int,
        val hitPoints: String,
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
    ) : Identifiable<String> {

        override val id: String
            @JsonIgnore
            get() = name
    }
}