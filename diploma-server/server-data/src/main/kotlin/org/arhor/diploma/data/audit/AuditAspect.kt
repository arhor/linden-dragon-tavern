package org.arhor.diploma.data.audit

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.Serializable
import java.lang.invoke.MethodHandles
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

@Aspect
@Component
class AuditAspect(private val auditEventRepository: AuditEventRepository) {

    private val auditMetadata: ConcurrentMap<KClass<*>, Audit> = ConcurrentHashMap()
    private val shouldNotBeAudited: MutableSet<KClass<*>> = ConcurrentSkipListSet()

    @Pointcut("@within(audit)")
    fun auditEnabled(audit: Audit) { /* no-op */ }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.BaseRepository+.save*(..))")
    fun saveEvent() { /* no-op */ }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.BaseRepository+.delete*(..))")
    fun deleteEvent() { /* no-op */ }

    @Around(value = "(saveEvent() || deleteEvent()) && target(domainObjRepository)")
    fun <T : DomainObject<K>, K : Serializable> handlePersistenceEvent(
        joinPoint: ProceedingJoinPoint,
        domainObjRepository: BaseRepository<T, K>,
    ): Any? {

        val repositoryClass = domainObjRepository::class

        if (shouldNotBeAudited.contains(repositoryClass)) {
            return joinPoint.proceed()
        }

        val auditAnnotation = findAuditAnnotation(repositoryClass)

        if (auditAnnotation == null) {
            shouldNotBeAudited.add(repositoryClass)
            return joinPoint.proceed()
        } else {
            auditMetadata[repositoryClass] = auditAnnotation as Audit
        }

        val result: Any? = joinPoint.proceed()

        auditEventRepository.saveAuditEvent(
            AuditEvent(
                id = null,
                type = AuditEvent.Type.CREATE,
                entityId = (result as DomainObject<Number>).id ?: -1,
                principal = "MAX",
                timestamp = LocalDateTime.now()
            ),
            auditAnnotation
        )

        return result
    }

    private fun findAuditAnnotation(repositoryClass: KClass<*>): Annotation? {
        return repositoryClass.supertypes.find { it.isSubtypeOf(REPOSITORY_TYPE) }
            ?.takeIf { it.javaType is Class<*> }
            ?.let { it.javaType as Class<*> }
            ?.annotations
            ?.find { it is Audit }
    }

    private fun <T> getDiff(oldObject: T, newObject: T): List<Pair<String, *>> {
        val diff = arrayListOf<Pair<String, *>>()

        oldObject!!::class.members

        return diff
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

        private val REPOSITORY_TYPE = BaseRepository::class.starProjectedType
    }
}