package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = Account.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR)
    @SequenceGenerator(name = SEQ_GENERATOR, sequenceName = SEQ_NAME, allocationSize = SEQ_ALLOC_SIZE)
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null,

    @Column(unique = true, nullable = false, length = 30)
    var username: String? = null,

    @Column(nullable = false, length = 512)
    var password: String? = null,

    @Column(unique = true, length = 128)
    var email: String? = null

) : AuditableDomainObject<Long>() {

    override val tableName: String
        get() = TABLE_NAME

    @OneToOne(optional = false)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    var accountDetails: AccountDetails? = null

    companion object {
        const val TABLE_NAME = "accounts"
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}
