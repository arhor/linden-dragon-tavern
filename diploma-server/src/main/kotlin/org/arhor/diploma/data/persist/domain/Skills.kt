package org.arhor.diploma.data.persist.domain

import org.arhor.diploma.commons.BitSet32
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Skills {

    @Column(name = "skills_bits")
    private var skillsBits: Int = 0

    fun setAcrobatics(value: Boolean = true) {
        set(SkillType.ACROBATICS, value)
    }

    fun getAcrobatics(): Boolean {
        return get(SkillType.ACROBATICS)
    }

    fun setAnimalHandling(value: Boolean = true) {
        set(SkillType.ANIMAL_HANDLING, value)
    }

    fun getAnimalHandling(): Boolean {
        return get(SkillType.ANIMAL_HANDLING)
    }

    private fun set(skillType: SkillType, value: Boolean) {
        skillsBits = BitSet32.fromNumber(skillsBits).also { it[skillType.ordinal] = value }.valuesHolder
    }

    private fun get(skillType: SkillType): Boolean {
        return BitSet32.fromNumber(skillsBits)[skillType.ordinal]
    }

    private enum class SkillType {
        ACROBATICS,
        ANIMAL_HANDLING,
        ARCANA,
        ATHLETICS,
        DECEPTION,
        HISTORY,
        INSIGHT,
        INTIMIDATION,
        INVESTIGATION,
        MEDICINE,
        NATURE,
        PERCEPTION,
        PERFORMANCE,
        PERSUASION,
        RELIGION,
        SLEIGHT_OF_HAND,
        STEALTH,
        SURVIVAL,
    }
}