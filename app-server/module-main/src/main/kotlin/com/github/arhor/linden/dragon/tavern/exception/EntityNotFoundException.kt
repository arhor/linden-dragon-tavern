package com.github.arhor.linden.dragon.tavern.exception

class EntityNotFoundException(
    name: String,
    condition: String,
) : PropertyConditionException(name, condition)
