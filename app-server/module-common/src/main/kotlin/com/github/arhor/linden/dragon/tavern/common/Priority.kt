package com.github.arhor.linden.dragon.tavern.common

enum class Priority(val value: Int) {

    FIRST  (value = Int.MIN_VALUE),

    HIGH   (-1_000_000_000),

    NORMAL (0),

    LOW    (1_000_000_000),

    LAST   (Int.MAX_VALUE),

    ;
}
