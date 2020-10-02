package org.arhor.diploma.data.persist.domain

import javax.persistence.Column
import javax.persistence.Embeddable
import kotlin.math.pow

@Embeddable
class Skills {

    @Column(name = "skills_bits")
    private var skillsBits: Int = 0

    fun setAcrobatics() {
        set(SkillType.ACROBATICS)
    }

    fun isAcrobatics(): Boolean {
        return get(SkillType.ACROBATICS)
    }

    private fun set(skillType: SkillType) {
        skillsBits = skillsBits or calcPosition(skillType)
    }

    private fun get(skillType: SkillType): Boolean {
        return (skillsBits and calcPosition(skillType)) == skillType.ordinal
    }

    private fun calcPosition(skillType: SkillType): Int {
        return 2.0.pow(skillType.ordinal).toInt()
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