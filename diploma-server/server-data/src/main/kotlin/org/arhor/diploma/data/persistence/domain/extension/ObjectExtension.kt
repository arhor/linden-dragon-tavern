package org.arhor.diploma.data.persistence.domain.extension

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "object_extension")
class ObjectExtension : Identifiable<ObjectExtension.CompositeId> {

    @EmbeddedId
    override var id: CompositeId? = null

    @Column(name = "field_type")
    var fieldType: String? = null

    @Column(name = "field_value")
    var fieldValue: String? = null

    @Embeddable
    data class CompositeId(
        @Column(name = "obj_id")     var objId: Long,
        @Column(name = "obj_table")  var objTable: String,
        @Column(name = "field_name") var fieldName: String,
    ) : Serializable
}