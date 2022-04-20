package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Account
import com.github.arhor.linden.dragon.tavern.service.dto.AccountDTO

interface AccountService : CrudService<Account, AccountDTO, Long>
