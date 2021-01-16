package org.arhor.diploma.data.persistence.domain.extension

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.CompositeId3
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "object_extension")
class ObjectExtension : Identifiable<CompositeId3<Long, String, String>> {

    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "value1", column = Column(name = "obj_id")),
        AttributeOverride(name = "value2", column = Column(name = "obj_table")),
        AttributeOverride(name = "value3", column = Column(name = "field_name")),
    )
    override var id: CompositeId3<Long, String, String>? = null

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