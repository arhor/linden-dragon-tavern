package org.arhor.diploma.service.mapping

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.config.mapping.IgnoreAuditProps
import org.arhor.diploma.config.mapping.MapStructConfig
import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapStructConfig::class)
interface AccountConverter : Converter<Account, AccountDTO> {

    @IgnoreAuditProps
    @Mapping(target = "securityProfile", ignore = true)
    override fun dtoToEntity(dto: AccountDTO): Account

    @InheritInverseConfiguration
    override fun entityToDto(entity: Account): AccountDTO
}