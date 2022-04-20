//package com.github.arhor.linden.dragon.tavern.data.persistence.audit
//
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.core.ResultSetExtractor
//import org.springframework.stereotype.Repository
//import java.util.concurrent.ConcurrentHashMap
//import java.util.concurrent.ConcurrentMap
//import javax.persistence.EntityManager
//import javax.persistence.PersistenceContext
//
//private val BOOL_RESULT: ResultSetExtractor<Boolean> = ResultSetExtractor {
//    it.next() && (it.getBoolean(1))
//}
//
//@Repository
//class AuditEventRepositoryImpl(private val db: JdbcTemplate) : AuditEventRepository {
//
//    @PersistenceContext
//    lateinit var em: EntityManager
//
//    private val existingAuditTables: ConcurrentMap<String, Boolean> = ConcurrentHashMap()
//    private val failedAuditTables: MutableSet<String> = HashSet()
//
//    override fun saveAuditEvent(event: AuditEvent, audit: Audit) {
//        if (ensureAuditTableExists(audit.table)) {
//            insertNewAuditRecord(audit.table, event)
//        }
//    }
//
//    private fun insertNewAuditRecord(auditTableName: String, event: AuditEvent) {
//        val (_, type, entityId, principal, timestamp) = event
//
////        db.update(
////            "INSERT INTO $auditTableName (type,entityId,principal,timestamp) VALUES (?,?,?,?)",
////            type,
////            entityId,
////            principal,
////            timestamp
////        )
//    }
//
//    private fun ensureAuditTableExists(table: String): Boolean {
//        val auditTableExists = existingAuditTables.computeIfAbsent(
//            table, this::dbTableExists
//        )
//        if (auditTableExists) {
//            return true
//        }
//        if (!failedAuditTables.contains(table)) {
//            return tryCreateMissingTable(table)
//        }
//        return false
//    }
//
//    private fun dbTableExists(tableName: String): Boolean {
//        return db.query(
//            "SELECT EXISTS(SELECT 1 FROM information_schema.tables WHERE table_name = '${tableName}')",
//            BOOL_RESULT,
//        ) ?: false
//    }
//
//    private fun tryCreateMissingTable(tableName: String): Boolean {
//        db.execute(
//            """
//            CREATE TABLE $tableName (
//                id           BIGSERIAL      NOT NULL PRIMARY KEY,
//                type         VARCHAR(10)    NOT NULL,
//                entityId     BIGINT         NOT NULL,
//                principal    VARCHAR(30)    NOT NULL,
//                timestamp    TIMESTAMP      NOT NULL
//            )
//            WITH (OIDS = FALSE);
//            """.trimIndent()
//        )
//        return if (dbTableExists(tableName)) {
//            true
//        } else {
//            failedAuditTables.add(tableName)
//            false
//        }
//    }
//}
