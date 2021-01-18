package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.STATIC_HASH_CODE
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(
    name = DomainObject.SEQ_GEN_NAME,
    sequenceName = "accounts_id_seq",
    allocationSize = DomainObject.SEQ_ALLOC_SIZE
)
class Account : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = "accounts"

    @Column(unique = true, nullable = false, length = 30)
    var username: String? = null

    @Column(nullable = false, length = 512)
    var password: String? = null

    @Column(unique = true, length = 128)
    var email: String? = null

    @Column(nullable = false, length = 60)
    var firstName: String? = null

    @Column(nullable = false, length = 60)
    var lastName: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    var securityProfile: SecurityProfile? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false
        if (!super.equals(other)) return false

        if (username != other.username) return false
        if (password != other.password) return false
        if (email != other.email) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        return STATIC_HASH_CODE
    }

    override fun toString(): String {
        return "Account(username=$username, password=$password, email=$email, firstName=$firstName, lastName=$lastName)"
    }
}
