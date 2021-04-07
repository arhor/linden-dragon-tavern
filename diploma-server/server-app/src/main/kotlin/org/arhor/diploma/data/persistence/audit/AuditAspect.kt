package org.arhor.diploma.data.persistence.audit

import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.io.Serializable
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

private val REPOSITORY_TYPE = BaseRepository::class.starProjectedType

@Aspect
//@Component
class AuditAspect(private val auditEventRepository: AuditEventRepository) {

    private val auditMetadata: ConcurrentMap<KClass<*>, Audit> = ConcurrentHashMap()
    private val shouldNotBeAudited: MutableSet<KClass<*>> = ConcurrentSkipListSet()

    @Pointcut("@within(audit)")
    fun auditEnabled(audit: Audit) { /* no-op */
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.BaseRepository+.save*(..))")
    fun saveEvent() { /* no-op */
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.BaseRepository+.delete*(..))")
    fun deleteEvent() { /* no-op */
    }

    @Around(value = "(saveEvent() || deleteEvent()) && target(repository)")
    fun <T, K> handlePersistenceEvent(jp: ProceedingJoinPoint, repository: BaseRepository<T, K>): Any?
            where T : DeletableDomainObject<K>,
                  K : Serializable {

        val repositoryClass = repository::class

        if (shouldNotBeAudited.contains(repositoryClass)) {
            return jp.proceed()
        }

        val auditAnnotation = findAuditAnnotation(repositoryClass)

        if (auditAnnotation == null) {
            shouldNotBeAudited.add(repositoryClass)
            return jp.proceed()
        } else {
            auditMetadata[repositoryClass] = auditAnnotation as Audit
        }

        val result: Any? = jp.proceed()

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
}