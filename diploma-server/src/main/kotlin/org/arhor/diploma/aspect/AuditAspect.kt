package org.arhor.diploma.aspect

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.AuditEventRepository
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.Serializable
import java.lang.invoke.MethodHandles

@Aspect
@Component
class AuditAspect(private val auditEventRepository: AuditEventRepository) {

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    fun repository() { /* no-op */
    }

    @Pointcut("!within(@org.arhor.diploma.data.persistence.DisableAudit *)")
    fun auditEnabled() { /* no-op */
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.*Repository.save*(..))")
    fun saveEvent() { /* no-op */
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.*Repository.delete*(..))")
    fun deleteEvent() { /* no-op */
    }

    @Around(value = "repository() && auditEnabled() && (saveEvent() || deleteEvent()) && target(repository)")
    fun <T : DomainObject<K>, K : Serializable> handlePersistenceEvent(
        joinPoint: ProceedingJoinPoint,
        repository: BaseRepository<T, K>
    ): Any {
        val arg = joinPoint.args.firstOrNull()

        if (arg is DomainObject<*>) {
            val newObject = arg as T
            val oldObject = repository.findById(newObject.getId()!!).orElse(null)

            if (oldObject != null) {
                try {
                    val result = joinPoint.proceed()

                    getDiff(oldObject, newObject)

                } catch (ex: Exception) {

                }
            }
        }


        return joinPoint.proceed()
    }

    fun <T> getDiff(oldObject: T, newObject: T): List<Pair<String, *>> {
        val diff = arrayListOf<Pair<String, *>>()

        oldObject!!::class.members

        return diff
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
        private const val REPOSITORY_PACKAGE = "org.arhor.diploma.data.persistence.repository"
    }
}