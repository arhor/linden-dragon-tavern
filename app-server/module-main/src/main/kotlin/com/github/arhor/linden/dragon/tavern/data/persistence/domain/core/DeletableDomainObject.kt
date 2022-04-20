package com.github.arhor.linden.dragon.tavern.data.persistence.domain.core

import com.github.arhor.linden.dragon.tavern.common.Deletable
import org.springframework.data.relational.core.mapping.Column
import java.io.Serializable

abstract class DeletableDomainObject<T : Serializable> : DomainObject<T>(),
    Deletable {

    @Column("deleted")
    override var isDeleted: Boolean = false
}
