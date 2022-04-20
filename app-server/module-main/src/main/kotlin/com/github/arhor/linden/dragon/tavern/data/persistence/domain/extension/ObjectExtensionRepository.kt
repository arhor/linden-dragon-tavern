//package com.github.arhor.linden.dragon.tavern.data.persistence.domain.extension
//
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.CrudRepository
//import org.springframework.stereotype.Repository
//
//@Repository
//interface ObjectExtensionRepository : CrudRepository<ObjectExtension, Long> {
//
//    @Query("SELECT e FROM ObjectExtension e WHERE e.id.objId = :id AND e.id.objTable = :table")
//    fun <K> findObjectExtensions(id: K, table: String): List<ObjectExtension>
//}
//
//fun <K> ObjectExtensionRepository.findObjectExtensionsAsMap(id: K, table: String): Map<String, Any?> {
//    return findObjectExtensions(id, table).associate { it.id.fieldName to it.fieldValue }
//}
