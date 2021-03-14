package org.arhor.diploma.config.mapping

import org.mapstruct.MapperConfig
import org.mapstruct.NullValueMappingStrategy

@MapperConfig(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
open class MapStructConfig