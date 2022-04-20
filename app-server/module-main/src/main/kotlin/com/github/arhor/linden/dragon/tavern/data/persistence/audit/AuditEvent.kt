//package com.github.arhor.linden.dragon.tavern.data.persistence.audit
//
//import java.time.LocalDateTime
//
//data class AuditEvent(
//    var id: Long?,
//    var type: Type,
//    var entityId: Number,
//    var principal: String,
//    var timestamp: LocalDateTime,
//) {
//
//    var data: List<AuditData> = mutableListOf()
//
//    enum class Type {
//        /**
//         *
//         */
//        CREATE,
//
//        /**
//         *
//         */
//        UPDATE,
//
//        /**
//         *
//         */
//        DELETE,
//        ;
//    }
//}
