package com.github.arhor.linden.dragon.tavern.exception;

abstract class PropertyConditionException(
    val name: String,
    val condition: String,
) : RuntimeException() {

    val params: Array<Any>
        get() = arrayOf(name, condition)
}
