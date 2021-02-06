package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Authority(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${SEQ_GEN_NAME}_authorities")
    @SequenceGenerator(
        name = "${SEQ_GEN_NAME}_authorities",
        sequenceName = "authorities_id_seq",
        allocationSize = SEQ_ALLOC_SIZE
    )
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null,

    @Column(name = "name", unique = true, nullable = false)
    var name: String? = null,
) : DomainObject<Long>() {

    override val tableName: String
        get() = "authorities"

    override fun hashCode(): Int {
        return classBasedStaticHashCode()
    }
}