package org.arhor.diploma.core

enum class AbilityName(val fullName: String) {

    STR("strength"),
    DEX("dexterity"),
    CON("constitution"),
    INT("intelligence"),
    WIS("wisdom"),
    CHA("charisma");

    companion object {
        @JvmStatic
        fun fromString(str: String): AbilityName {
            for (ability in values()) {
                if (ability.fullName == str) {
                    return ability
                }
            }
            throw IllegalArgumentException(str)
        }
    }

}