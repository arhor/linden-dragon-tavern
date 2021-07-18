package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(AccountDetails.TABLE_NAME)
data class AccountDetails(

    @Id
    @Column("id")
    override var id: Long? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null

) : AuditableDomainObject<Long>() {

    companion object {
        const val TABLE_NAME = "account_details"
    }
}
