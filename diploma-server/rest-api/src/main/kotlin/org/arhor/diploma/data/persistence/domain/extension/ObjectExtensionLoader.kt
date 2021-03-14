package org.arhor.diploma.data.persistence.domain.extension

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.*

@Component
class ObjectExtensionLoader {

    @Autowired
    fun init(repository: ObjectExtensionRepository) {
        ObjectExtensionLoader.repository = repository
    }

    @PostLoad
    fun postLoad(obj: DomainObject<*>) {
        obj.extraData.dataProvider = { repository.findObjectExtensionsAsMap(obj.id, obj.tableName) }
        obj.extraData.dataConsumer = { data -> println(data) }
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