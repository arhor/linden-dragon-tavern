package org.arhor.diploma.service.mapping.impl

import org.arhor.diploma.domain.Account
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.mapping.Mapper
import org.springframework.stereotype.Component

@Component
class AccountMapper : Mapper<Account, AccountDTO> {


    override fun map(source: Account): AccountDTO {
        return AccountDTO(
            id = source.getId(),
            email = source.email,
            firstName = source.firstName,
            lastName = source.lastName,
            username = source.username,
            password = source.password
        )
    }
}