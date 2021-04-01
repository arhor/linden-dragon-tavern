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
        /* no-op */
    }

    @PreUpdate
    fun preUpdate(obj: DomainObject<*>) {
        /* no-op */
    }

    @PreRemove
    fun preRemove(obj: DomainObject<*>) {
        /* no-op */
    }

    @PostRemove
    fun postRemove(obj: DomainObject<*>) {
        /* no-op */
    }

    @PostUpdate
    fun postUpdate(obj: DomainObject<*>) {
        /* no-op */
    }

    @PostPersist
    fun postPersist(obj: DomainObject<*>) {
        /* no-op */
    }

    companion object {
        private lateinit var repository: ObjectExtensionRepository
    }
}