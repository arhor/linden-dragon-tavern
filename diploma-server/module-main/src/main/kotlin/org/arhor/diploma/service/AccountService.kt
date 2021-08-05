package org.arhor.diploma.service

import org.arhor.diploma.data.persistence.domain.Account
import org.arhor.diploma.service.dto.AccountDTO

interface AccountService : CrudService<Account, AccountDTO, Long>
