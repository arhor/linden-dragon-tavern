package com.github.arhor.linden.dragon.tavern.config.mapping

import org.mapstruct.MapperConfig
import org.mapstruct.NullValueMappingStrategy

@MapperConfig(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
open class MapStructConfig
