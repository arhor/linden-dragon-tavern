package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.classBasedStaticHashCode
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(Authority.TABLE_NAME)
data class Authority(

    @Id
    @Column("id")
    override var id: Long? = null,

    @Column("name")
    var name: String? = null

) : DomainObject<Long>() {

    override val tableName: String
        get() = "authorities"

    override fun hashCode(): Int = classBasedStaticHashCode()

    companion object {
        const val TABLE_NAME = "authorities"
        const val SEQ_GENERATOR = "${SEQ_GEN_NAME}_${TABLE_NAME}"
        const val SEQ_NAME = "${TABLE_NAME}_id_seq"
    }
}