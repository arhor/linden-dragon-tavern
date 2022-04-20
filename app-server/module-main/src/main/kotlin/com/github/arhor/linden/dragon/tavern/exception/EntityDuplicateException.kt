package com.github.arhor.linden.dragon.tavern.exception

class EntityDuplicateException(
    name: String,
    condition: String,
) : PropertyConditionException(name, condition)
