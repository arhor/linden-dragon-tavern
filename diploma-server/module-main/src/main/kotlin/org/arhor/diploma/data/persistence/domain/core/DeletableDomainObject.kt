package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Deletable
import org.springframework.data.relational.core.mapping.Column
import java.io.Serializable

abstract class DeletableDomainObject<T : Serializable> : DomainObject<T>(),
    Deletable {

    @Column("deleted")
    override var isDeleted: Boolean = false
}