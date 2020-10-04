package org.arhor.diploma.service.mapping

import org.arhor.diploma.data.persist.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface AccountConverter : Converter<Account, AccountDTO> {

    @Mappings(
        Mapping(target = "securityProfile", ignore = true),
        Mapping(target = "created", ignore = true),
        Mapping(target = "updated", ignore = true),
        Mapping(target = "deleted", ignore = true)
    )
    override fun dtoToEntity(dto: AccountDTO): Account

    @InheritInverseConfiguration
    override fun entityToDto(entity: Account): AccountDTO
}