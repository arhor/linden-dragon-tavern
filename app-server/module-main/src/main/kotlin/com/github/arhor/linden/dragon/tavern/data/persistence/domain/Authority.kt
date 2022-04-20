package com.github.arhor.linden.dragon.tavern.data.persistence.domain

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DomainObject
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(Authority.TABLE_NAME)
data class Authority(

    @Id
    override var id: Long? = null,

    var name: String? = null

) : DomainObject<Long>() {

    companion object {
        const val TABLE_NAME = "authorities"
    }
}
