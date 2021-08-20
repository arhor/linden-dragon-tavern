package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.arhor.diploma.Role.USER
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.SecurityProfileRepository
import org.arhor.diploma.service.AccountService
import org.arhor.diploma.service.dto.AccountDTO
import org.arhor.diploma.service.mapping.AccountConverter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val securityProfileRepository: SecurityProfileRepository,
    private val converter: AccountConverter,
) : AccountService {

    @Transactional
    override suspend fun create(item: AccountDTO): AccountDTO {
        item.username?.takeIf { accountRepository.existsByUsername(it) }?.let {
            throw RuntimeException("Username '$it' is already taken")
        }

        val (userProfileId, _, _) = securityProfileRepository.findByRole(USER)

        val createdAccount = converter.mapDtoToEntity(item)
            .apply { profileId = userProfileId }
            .let { accountRepository.save(it) }

        return converter.mapEntityToDto(createdAccount)
    }

    override suspend fun getOne(id: Long): AccountDTO {
        return accountRepository.findById(id)?.let { converter.mapEntityToDto(it) }
            ?: throw EntityNotFoundException("Account", "id", id)
    }

    override fun getList(): Flow<AccountDTO> {
        return accountRepository.findAll().map(converter::mapEntityToDto)
    }

    override fun getList(page: Int, size: Int): Flow<AccountDTO> {
        return accountRepository.findAll().drop(page * size).take(size).map(converter::mapEntityToDto)
    }

    override suspend fun getTotalSize(): Long {
        return accountRepository.count()
    }

    override suspend fun update(item: AccountDTO): AccountDTO {
        if (item.id != null) {
            val id = item.id!!
            if (accountRepository.existsById(id)) {
                return converter.mapDtoToEntity(item)
                    .let { accountRepository.save(it) }
                    .let(converter::mapEntityToDto)
            } else {
                throw EntityNotFoundException("Account", "id", id)
            }
        } else {
            throw IllegalArgumentException("Entity id must not be null")
        }
    }

    override suspend fun delete(id: Long) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id)
        } else {
            throw EntityNotFoundException("Account", "id", id)
        }
    }

    override suspend fun delete(item: AccountDTO) {
        item.id?.let { delete(it) }
    }
}
