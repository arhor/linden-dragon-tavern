package com.github.arhor.linden.dragon.tavern.service.mapping

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.config.mapping.IgnoreAuditProps
import com.github.arhor.linden.dragon.tavern.config.mapping.MapStructConfig
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Account
import com.github.arhor.linden.dragon.tavern.service.dto.AccountDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapStructConfig::class)
interface AccountConverter : Converter<Account, AccountDTO> {

    @IgnoreAuditProps
    @Mapping(target = "profileId", ignore = true)
    override fun mapDtoToEntity(item: AccountDTO): Account

    @InheritInverseConfiguration
    override fun mapEntityToDto(item: Account): AccountDTO
}
