package org.arhor.diploma.data.persistence.domain.extension

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.*

@Component
class ObjectExtensionLoader {

    @Autowired
    fun init(database: EntityManager) {
        ObjectExtensionLoader.database = database
    }

    @PostLoad
    fun loadExtendedState(obj: DomainObject<*>) {
        database.createQuery(
            """
            SELECT ext
            FROM ObjectExtension ext
            WHERE ext.id.value1 = ?1
              AND ext.id.value2 = ?2
            """.trimIndent(),
            OBJ_EXT_TYPE)
            .setParameter(1, obj.id)
            .setParameter(2, obj.tableName)
            .resultList
            .forEach { obj[it.id!!.value3!!] = it.fieldValue }
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
        private val OBJ_EXT_TYPE = ObjectExtension::class.java

        private lateinit var database: EntityManager
    }
}