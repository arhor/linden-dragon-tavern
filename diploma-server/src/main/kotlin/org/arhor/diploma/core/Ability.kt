package org.arhor.diploma.core

import org.arhor.diploma.core.AbilityName.*

sealed class Ability(val name: AbilityName, open val modifier: Int)

data class Strength(override val modifier: Int) : Ability(STR, modifier)

data class Dexterity(override val modifier: Int) : Ability(DEX, modifier)

data class Constitution(override val modifier: Int) : Ability(CON, modifier)

data class Intelligence(override val modifier: Int) : Ability(INT, modifier)

data class Wisdom(override val modifier: Int) : Ability(WIS, modifier)

data class Charisma(override val modifier: Int) : Ability(CHA, modifier)
