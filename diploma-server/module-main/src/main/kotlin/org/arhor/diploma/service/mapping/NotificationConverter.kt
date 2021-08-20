package org.arhor.diploma.service.mapping

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.config.mapping.MapStructConfig
import org.arhor.diploma.data.persistence.domain.Notification
import org.arhor.diploma.service.dto.NotificationDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = MapStructConfig::class)
interface NotificationConverter : Converter<Notification, NotificationDTO> {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "timestamp", ignore = true),
        Mapping(target = "accountId", ignore = true),
    )
    override fun mapDtoToEntity(item: NotificationDTO): Notification

    @InheritInverseConfiguration
    override fun mapEntityToDto(item: Notification): NotificationDTO
}