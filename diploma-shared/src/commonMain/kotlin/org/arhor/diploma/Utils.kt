package org.arhor.diploma

import kotlin.js.JsName
import kotlin.jvm.JvmName

object Utils {

    @JsName("calcAbilityModifier")
    @JvmName("calcAbilityModifier")
    fun calcAbilityModifier(value: Number): String  {
        val modifier = (value.toInt() - 10) / 2
        return if (modifier < 0) {
            "-${modifier}"
        } else {
            "+${modifier}"
        }
    }
}