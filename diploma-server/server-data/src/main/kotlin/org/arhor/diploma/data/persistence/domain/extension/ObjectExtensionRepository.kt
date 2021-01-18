package org.arhor.diploma.data.persistence.domain.extension

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ObjectExtensionRepository : CrudRepository<ObjectExtension, Long> {

    @Query("SELECT e FROM object_extension e WHERE e.obj_id = :id AND e.obj_table = :table", nativeQuery = true)
    fun <K> findObjectExtensions(id: K, table: String): List<ObjectExtension>
}