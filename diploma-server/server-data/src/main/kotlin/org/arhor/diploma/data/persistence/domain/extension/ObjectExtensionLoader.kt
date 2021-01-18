package org.arhor.diploma.data.persistence.domain.extension

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.*

@Component
class ObjectExtensionLoader {

    @Autowired
    fun init(database: ObjectExtensionRepository) {
        ObjectExtensionLoader.repository = database
    }

    @PostLoad
    fun loadExtendedState(obj: DomainObject<*>) {
        repository
            .findObjectExtensions(obj.id, obj.tableName)
            .associate { it.id!!.fieldName to it.fieldValue }


    }

    @PrePersist
    fun prePersist(obj: DomainObject<*>) {
        println(obj)
    }

    @PreUpdate
    fun preUpdate(obj: DomainObject<*>) {
        println(obj)
    }

    @PreRemove
    fun preRemove(obj: DomainObject<*>) {
        println(obj)
    }

    @PostRemove
    fun postRemove(obj: DomainObject<*>) {
        println(obj)
    }

    @PostUpdate
    fun postUpdate(obj: DomainObject<*>) {
        println(obj)
    }

    @PostPersist
    fun postPersist(obj: DomainObject<*>) {
        println(obj)
    }

    companion object {
        private lateinit var repository: ObjectExtensionRepository
    }
}