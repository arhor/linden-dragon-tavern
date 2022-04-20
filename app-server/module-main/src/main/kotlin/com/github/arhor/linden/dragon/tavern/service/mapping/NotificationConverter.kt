package com.github.arhor.linden.dragon.tavern.service.mapping

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.config.mapping.MapStructConfig
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Notification
import com.github.arhor.linden.dragon.tavern.service.dto.NotificationDTO
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
