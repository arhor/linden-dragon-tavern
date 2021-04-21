//package org.arhor.diploma.data.persistence.domain
//
//import org.arhor.diploma.data.classBasedStaticHashCode
//import org.arhor.diploma.data.persistence.domain.core.CompositeId2
//import org.springframework.data.relational.core.mapping.Table
//import javax.persistence.*
//
//@Table("security_profile_authorities")
//data class SecurityProfileAuthority(
//
//    @EmbeddedId
//    @AttributeOverrides(
//        AttributeOverride(name = "value1", column = Column(name = "profile_id", nullable = false)),
//        AttributeOverride(name = "value2", column = Column(name = "authority_id", nullable = false))
//    )
//    var id: CompositeId2<Long, Long>? = null
//
//) {
//
//    override fun hashCode(): Int = classBasedStaticHashCode()
//}