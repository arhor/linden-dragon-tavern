package org.arhor.diploma.commons

enum class Priority(val value: Int) {

    FIRST(Int.MIN_VALUE),

    HIGH(-1_000_000_000),

    NORMAL(0),

    LOW(1_000_000_000),

    LAST(Int.MAX_VALUE),
}