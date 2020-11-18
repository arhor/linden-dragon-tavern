package org.arhor.diploma.config.mapping

import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@Mappings(
    Mapping(target = "created", ignore = true),
    Mapping(target = "updated", ignore = true),
    Mapping(target = "deleted", ignore = true),
)
annotation class IgnoreAuditProps